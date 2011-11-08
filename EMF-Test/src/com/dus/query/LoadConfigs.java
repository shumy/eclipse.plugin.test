package com.dus.query;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;

public class LoadConfigs<T extends EObject> {
	public enum Option {EAGER, LAZY};
	
	private final HashMap<String, Option> loadOptions = new HashMap<String, Option>();
	
	public void add(String field, Option option) {
		loadOptions.put(field, option);
	}
}
