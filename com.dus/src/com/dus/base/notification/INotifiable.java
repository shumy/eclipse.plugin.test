package com.dus.base.notification;

import com.dus.list.IList;

public interface INotifiable {
	
	//default is disabled
	boolean isNotificationsEnabled();		
	void setNofiticationsEnabled(boolean enabled);
	
	IList<INotificationListener> getListeners();
}
