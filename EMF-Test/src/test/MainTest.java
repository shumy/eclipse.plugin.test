package test;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import test.domain.domain.DomainFactory;
import test.domain.domain.User;

public class MainTest {

	public static void main(String[] args) throws Exception {
		DomainFactory factory = DomainFactory.eINSTANCE;

		User user = factory.createUser();
		user.setName("Micael");
		user.setPass("password");
	
		
		/* Use Register method...
		
		//Register the XMI resource factory for the .domain extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("domain", new XMIResourceFactoryImpl());

		
		/ResourceSet resSet = new ResourceSetImpl();
		/Resource resource = resSet.createResource(URI.createURI("data/mydata.domain"));
		
		*/
		Resource resource = new XMIResourceImpl(URI.createURI("data/mydata.domain"));
		
		resource.getContents().add(user);
		resource.save(Collections.EMPTY_MAP);

	}

}
