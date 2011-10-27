package org.eclipse.graphiti.tutorial.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.ConnectionCreationToolEntry;
import org.eclipse.graphiti.palette.impl.ObjectCreationToolEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.graphitiArch.BusinessObjectHelper;
import org.graphitiArch.Elements;
import org.graphitiArch.IElement;
import org.graphitiArch.IToolBehavior;
import org.graphitiArch.has.IHasToolBehavior;

public class ToolBehaviorProvider extends DefaultToolBehaviorProvider {

	public ToolBehaviorProvider(IDiagramTypeProvider dp) {super(dp);}
	
	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pe = context.getPictogramElement();
		
		Class<?> businessObjectClass = BusinessObjectHelper.getBusinessClassFromPictogramElement(pe);
		
		IElement<?> element = Elements.getInstance().getFromBusinessClass(businessObjectClass);
		if(element instanceof IHasToolBehavior) {
			IToolBehavior tb = ((IHasToolBehavior) element).getToolBehavior();
			setGenericContextButtons(data, pe, tb.getGenericContextButtons());
			tb.setupContextButtonPad(data, context);
		}
		
		return data;
	}
	
    @Override
    public IPaletteCompartmentEntry[] getPalette() {
		IFeatureProvider featureProvider = getFeatureProvider();
		
		List<IPaletteCompartmentEntry> ret = new ArrayList<IPaletteCompartmentEntry>();

		// add new compartment at the end of the existing compartments
		PaletteCompartmentEntry compartmentEntry = new PaletteCompartmentEntry("Elements", null);
		ret.add(compartmentEntry);

		// add new stack entry to new compartment
		//StackEntry stackEntry = new StackEntry("EObject", "EObject", null);
		//compartmentEntry.addToolEntry(stackEntry);

		// add all create-features to the new stack-entry
		ICreateFeature[] createFeatures = featureProvider.getCreateFeatures();
		for (ICreateFeature cf : createFeatures) {
			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
			
			compartmentEntry.addToolEntry(objectCreationToolEntry);
			//stackEntry.addCreationToolEntry(objectCreationToolEntry);
		}

		// add all create-connection-features to the new stack-entry
		ICreateConnectionFeature[] createConnectionFeatures = featureProvider.getCreateConnectionFeatures();
		for (ICreateConnectionFeature cf : createConnectionFeatures) {
			ConnectionCreationToolEntry connectionCreationToolEntry = new ConnectionCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId());
			connectionCreationToolEntry.addCreateConnectionFeature(cf);
			
			compartmentEntry.addToolEntry(connectionCreationToolEntry);
			//stackEntry.addCreationToolEntry(connectionCreationToolEntry);
		}

		return ret.toArray(new IPaletteCompartmentEntry[ret.size()]);
    }
}
