package com.dus.base;

import com.dus.base.notification.INotifiable;
import com.dus.base.reflection.IReflected;


public interface IEntity extends INotifiable, IReflected {	
	EntityID getId();
	
	boolean validate();
}
