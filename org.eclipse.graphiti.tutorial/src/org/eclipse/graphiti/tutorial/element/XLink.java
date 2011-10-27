package org.eclipse.graphiti.tutorial.element;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.tutorial.element.xlink.AddLinkFeature;
import org.eclipse.graphiti.tutorial.element.xlink.CreateLinkFeature;
import org.graphitiArch.IElement;
import org.graphitiArch.has.IHasAddFeature;
import org.graphitiArch.has.IHasCreateConnectionFeature;

public class XLink implements IElement<EReference>, IHasCreateConnectionFeature, IHasAddFeature {
	
	private final CreateLinkFeature createFeature;
	private final AddLinkFeature addFeature;
	
	public XLink(IFeatureProvider fp) {
		this.createFeature = new CreateLinkFeature(fp);
		this.addFeature = new AddLinkFeature(fp);
	}
	
	@Override
	public Class<EReference> getBusinessClass() {return EReference.class;}
	
	@Override
	public ICreateConnectionFeature getCreateConnectionFeature() {return createFeature;}
	
	@Override
	public IAddFeature getAddFeature() {return addFeature;}

	
	
	public Object createLink(Object sourceObject, Object targetObject) {
		EClass source = (EClass) sourceObject;
		EClass target = (EClass) targetObject;
		
		EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setName("link");
		eReference.setEType(target);
		eReference.setLowerBound(0);
		eReference.setUpperBound(1);
		source.getEStructuralFeatures().add(eReference);
		return eReference;
	}
}
