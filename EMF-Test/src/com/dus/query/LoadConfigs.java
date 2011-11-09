package com.dus.query;

import java.util.HashMap;
import java.util.Set;

public class LoadConfigs {
	public enum Option {EAGER, LAZY};
	
	private final HashMap<String, Option> loadOptions = new HashMap<String, Option>();
	
	public boolean isEmpty() {
		return loadOptions.isEmpty();
	}
	
	public void add(String field, Option option) {
		loadOptions.put(field, option);
	}
	
	public Set<String> getFields() {
		return loadOptions.keySet();
	}
	
	public Option getValue(String field) {
		return loadOptions.get(field);
	}
}
