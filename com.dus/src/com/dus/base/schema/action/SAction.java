package com.dus.base.schema.action;

import com.dus.base.schema.SFeature;

public interface SAction extends SFeature {
	
	//default is enabled
	boolean isExecutorsEnabled();
	void setExecutorsEnabled(boolean enabled);
	
	IActionExecutor getExecutor();
	void setExecutor(IActionExecutor executor);
}
