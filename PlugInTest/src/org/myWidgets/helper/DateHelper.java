package org.myWidgets.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum DateHelper {
	INSTANCE;
	
	private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public Date createDate(int year, int month, int day) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month);
	    cal.set(Calendar.DATE, day);
	    
	    return cal.getTime();
	}
	
	public String format(Date date) {
		return formater.format(date);
	}
}
