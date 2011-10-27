package org.graphitiArch;

import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.tb.IContextButtonPadData;

public interface IToolBehavior {
	public static int CONTEXT_BUTTON_UPDATE = 1 << 1;
	public static int CONTEXT_BUTTON_REMOVE = 1 << 2;
	public static int CONTEXT_BUTTON_DELETE = 1 << 3;
	
	int getGenericContextButtons();
	
	void setupContextButtonPad(IContextButtonPadData data, IPictogramElementContext context);
	
}
