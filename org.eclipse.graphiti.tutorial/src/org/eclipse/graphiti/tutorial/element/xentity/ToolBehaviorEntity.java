package org.eclipse.graphiti.tutorial.element.xentity;

import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.ContextEntryHelper;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tutorial.ImageProvider;
import org.eclipse.graphiti.tutorial.element.XEntity;
import org.eclipse.graphiti.tutorial.element.XLink;
import org.graphitiArch.Elements;
import org.graphitiArch.IToolBehavior;

public class ToolBehaviorEntity implements IToolBehavior {

	@Override
	public int getGenericContextButtons() {
		return CONTEXT_BUTTON_DELETE | CONTEXT_BUTTON_REMOVE | CONTEXT_BUTTON_UPDATE;
	}
	
	@Override
	public void setupContextButtonPad(IContextButtonPadData data, IPictogramElementContext context) {
		PictogramElement pe = context.getPictogramElement();
		XEntity xentity = Elements.getInstance().get(XEntity.class);
		XLink xlink = Elements.getInstance().get(XLink.class);
		
		//set the collapse button
		CustomContext cc = new CustomContext(new PictogramElement[] {pe});
		IContextButtonEntry collapseButton = ContextEntryHelper.createCollapseContextButton(true, xentity.getCollapseFeature(), cc);
		data.setCollapseContextButton(collapseButton);
		
		//set the collapse button
		ContextButtonEntry button = new ContextButtonEntry(null, context);
		button.setText("Create link");
		button.setIconId(ImageProvider.IMG_LINK);
		button.addDragAndDropFeature(xlink.getCreateConnectionFeature());
		data.getDomainSpecificContextButtons().add(button);
	}

}
