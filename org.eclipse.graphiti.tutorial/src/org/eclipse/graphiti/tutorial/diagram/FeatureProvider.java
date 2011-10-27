package org.eclipse.graphiti.tutorial.diagram;

import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;
import org.graphitiArch.BusinessObjectHelper;
import org.graphitiArch.Elements;
import org.graphitiArch.IElement;
import org.graphitiArch.has.IHasAddFeature;
import org.graphitiArch.has.IHasCreateConnectionFeature;
import org.graphitiArch.has.IHasCreateFeature;
import org.graphitiArch.has.IHasCustomFeatures;
import org.graphitiArch.has.IHasLayoutFeature;

public class FeatureProvider extends DefaultFeatureProvider {
	private final HashMap<Class<?>, IAddFeature> addFeatures = new HashMap<Class<?>, IAddFeature>();
	private final HashMap<Class<?>, ILayoutFeature> layoutFeatures = new HashMap<Class<?>, ILayoutFeature>();
	
	private ICreateFeature[] createFeatures;
	private ICreateConnectionFeature[] createConnectionFeatures;
	private ICustomFeature[] customFeatures;

    FeatureProvider(IDiagramTypeProvider dtp) {super(dtp);}
    
    public void setup(Elements elements) {
    	LinkedList<ICreateFeature> createFeatureList = new LinkedList<ICreateFeature>();
    	LinkedList<ICreateConnectionFeature> createConnectionFeatureList = new LinkedList<ICreateConnectionFeature>();
    	LinkedList<ICustomFeature> customFeatureList = new LinkedList<ICustomFeature>();
    	
    	for(IElement<?> el : elements.getAll()) {
    		if(el instanceof IHasCreateFeature)
    			createFeatureList.add(((IHasCreateFeature) el).getCreateFeature());
    		
    		if(el instanceof IHasCreateConnectionFeature)
    			createConnectionFeatureList.add(((IHasCreateConnectionFeature) el).getCreateConnectionFeature());
    		
    		if(el instanceof IHasCustomFeatures)
    			customFeatureList.addAll(((IHasCustomFeatures) el).getCustomFeatures());
    		
    		if(el instanceof IHasAddFeature)
    			addFeatures.put(el.getBusinessClass(), ((IHasAddFeature) el).getAddFeature());
    		
    		if(el instanceof IHasLayoutFeature)
    			layoutFeatures.put(el.getBusinessClass(), ((IHasLayoutFeature) el).getLayoutFeature()); 
    	}
    	
    	createFeatures = createFeatureList.toArray(new ICreateFeature[createFeatureList.size()]);
    	createConnectionFeatures = createConnectionFeatureList.toArray(new ICreateConnectionFeature[createConnectionFeatureList.size()]);
    	customFeatures = customFeatureList.toArray(new ICustomFeature[customFeatureList.size()]);
    }
    
    
	//-------------------------------------------------------------------------------------------------------------------  
    @Override
    public ICreateFeature[] getCreateFeatures() {return createFeatures;}
    
    @Override
    public ICreateConnectionFeature[] getCreateConnectionFeatures() {return createConnectionFeatures;}
    
    @Override
    public ICustomFeature[] getCustomFeatures(ICustomContext context) {return customFeatures;}
    
    @Override
    public IAddFeature getAddFeature(IAddContext context) {
    	Class<?> businessObjectClass = BusinessObjectHelper.getBusinessClassFromObject(context.getNewObject());
    	if(addFeatures.containsKey(businessObjectClass))
    		return addFeatures.get(businessObjectClass);
        
        return super.getAddFeature(context);
    }
    
    @Override
    public ILayoutFeature getLayoutFeature(ILayoutContext context) {
        Class<?> businessObjectClass = BusinessObjectHelper.getBusinessClassFromPictogramElement(context.getPictogramElement());
        if(layoutFeatures.containsKey(businessObjectClass))
        	return layoutFeatures.get(businessObjectClass);
        
        return super.getLayoutFeature(context);
    }
    
    @Override
    public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
        // simply return all create connection features
        return getCreateConnectionFeatures();
    }
}
