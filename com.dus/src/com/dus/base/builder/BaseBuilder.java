package com.dus.base.builder;

import com.dus.base.IEntity;
import com.dus.context.Context;


public class BaseBuilder<T extends IEntity> implements IBuilder<T> {
	private Class<T> type;
	
	public BaseBuilder(Class<T> type) {
		this.type = type;
	}
	
	public T build() {
		System.out.println("build: " + type.getName());
		return Context.getData().getSession().create(type, this);
	}
}
