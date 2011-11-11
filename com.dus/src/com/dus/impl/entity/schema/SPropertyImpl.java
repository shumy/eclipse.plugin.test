package com.dus.impl.entity.schema;

import com.dus.base.schema.SProperty;

public class SPropertyImpl implements SProperty {
	private final String name;
	private final boolean isMany;
	
	public SPropertyImpl(String name, boolean isMany) {
		this.name = name;
		this.isMany = isMany;
	}
	
	@Override
	public String getName() {return name;}

	@Override
	public boolean isMany() {return isMany;}

}
