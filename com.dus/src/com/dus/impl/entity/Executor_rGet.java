package com.dus.impl.entity;

import com.dus.base.EntityID;
import com.dus.base.schema.SProperty;
import com.dus.impl.Store;

public class Executor_rGet implements IMethodExecutor {

	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		Store store = handler.getStore();
		
		SProperty property = (SProperty) parameters[0];
		Object value = store.getProperty(handler.getId(), property);
		
		if(value instanceof EntityID) {
			return store.getEntity((EntityID)value);
		} else {
			//TODO: process lists?
			return value;
		}
	}
	
}
