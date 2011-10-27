package org.eclipse.graphiti.tutorial.element;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.tutorial.element.xentity.AddEntityFeature;
import org.eclipse.graphiti.tutorial.element.xentity.CollapseEntityFeature;
import org.eclipse.graphiti.tutorial.element.xentity.CreateEntityFeature;
import org.eclipse.graphiti.tutorial.element.xentity.LayoutEntityFeature;
import org.eclipse.graphiti.tutorial.element.xentity.ToolBehaviorEntity;
import org.graphitiArch.IElement;
import org.graphitiArch.IToolBehavior;
import org.graphitiArch.has.IHasAddFeature;
import org.graphitiArch.has.IHasCreateFeature;
import org.graphitiArch.has.IHasCustomFeatures;
import org.graphitiArch.has.IHasLayoutFeature;
import org.graphitiArch.has.IHasToolBehavior;

public class XEntity implements IElement<EClass>, IHasCreateFeature, IHasAddFeature, IHasLayoutFeature, IHasToolBehavior, IHasCustomFeatures {
		
	private final AddEntityFeature addFeature;
	private final CreateEntityFeature createFeature;
	private final LayoutEntityFeature layoutFeature;
	private final ToolBehaviorEntity toolBehavior;
	
	//custom features:
	private HashMap<Class<?>, ICustomFeature> customFeatureMap = new HashMap<Class<?>, ICustomFeature>(); 
	
	public XEntity(IFeatureProvider fp){
		this.addFeature = new AddEntityFeature(fp);
		this.createFeature = new CreateEntityFeature(fp);
		this.layoutFeature = new LayoutEntityFeature(fp);
		this.toolBehavior = new ToolBehaviorEntity();
		
		customFeatureMap.put(CollapseEntityFeature.class, new CollapseEntityFeature(fp));
	}
	
	@Override
	public Class<EClass> getBusinessClass() {return EClass.class;}
	
	@Override
	public IAddFeature getAddFeature() {return addFeature;}
	
	@Override
	public ICreateFeature getCreateFeature() {return createFeature;}
	
	@Override
	public ILayoutFeature getLayoutFeature() {return layoutFeature;}
	
	@Override
	public IToolBehavior getToolBehavior() {return toolBehavior;}

	@Override
	public Collection<ICustomFeature> getCustomFeatures() {return customFeatureMap.values();}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ICustomFeature> T getCustom(Class<T> customFeatureClass) {
		return (T) customFeatureMap.get(customFeatureClass);
	}
	
	public CollapseEntityFeature getCollapseFeature() {
		return (CollapseEntityFeature) customFeatureMap.get(CollapseEntityFeature.class);
	}
	
	public EObject createEntity(String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		return eClass;
	}
}
