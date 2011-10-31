package test;

import java.util.Collections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import test.domain.Address;
import test.domain.DomainFactory;
import test.domain.DomainPackage;
import test.domain.User;

public class MainTest {
	public static String dataFile = "data/mydata.domain";
	
	public static void main(String[] args) throws Exception {
		DomainFactory dFactory = DomainFactory.eINSTANCE;
		DomainPackage pFactory = DomainPackage.eINSTANCE;
		
		Adapter adapter = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				System.out.println("Notfication received from the data model. Data model has changed!!!");
			}
		};
		
		
		EObject eObject = EcoreFactory.eINSTANCE.create(EcorePackage.eINSTANCE.getEObject());
		eObject.eAdapters().add(adapter);
		
		
		EAttribute otherAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		otherAttribute.setName("other");
		otherAttribute.setEType(EcorePackage.eINSTANCE.getEString());
		
		EClass extendedAddress = EcoreFactory.eINSTANCE.createEClass();
		extendedAddress.setName("XAddress");
		extendedAddress.getEStructuralFeatures().add(otherAttribute);
		extendedAddress.getESuperTypes().add(pFactory.getAddress());
		
		EPackage extPackage = EcoreFactory.eINSTANCE.createEPackage();
		extPackage.setName("ext");
		extPackage.setNsPrefix("domain.ext");
		extPackage.setNsURI("http://domain/1.1");
		extPackage.getEClassifiers().add(extendedAddress);
		EPackage.Registry.INSTANCE.put(extPackage.getNsURI(), extPackage);
		
		pFactory.getESubpackages().add(extPackage);
		
		//--------------------------------------------------------------------------------
		Address address = (Address) extPackage.getEFactoryInstance().create(extendedAddress);
		address.setLocal("Aveiro");
		
		//TODO: try to use commands!!!
		address.eSet(otherAttribute, "Other value");
		
		User user = dFactory.createUser();
		user.setName("Micael");
		user.setPass("password");
		user.getAddresses().add(address);
	
		System.out.println(user.eClass().getName());
		
		for(EAttribute attrib : user.eClass().getEAllAttributes()) {
			System.out.println("A: " + attrib.getName() + " V: " + user.eGet(attrib));
			//System.out.println();
		}
		
		/* Use Register method...
		
		//Register the XMI resource factory for the .domain extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("domain", new XMIResourceFactoryImpl());

		
		/ResourceSet resSet = new ResourceSetImpl();
		/Resource resource = resSet.createResource(URI.createURI("data/mydata.domain"));
		*/
		
		
		System.out.println("Save to: " + dataFile);
		Resource resource = new XMIResourceImpl(URI.createURI("data/mydata.domain"));
		
		
		
		resource.getContents().add(user);
		resource.save(Collections.EMPTY_MAP);

	}

}
