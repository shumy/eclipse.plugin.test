package com.dus.impl;

import java.lang.reflect.Field;
import java.util.UUID;

import com.dus.ISession;
import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.builder.IBuilder;
import com.dus.base.finder.FetchConfig;
import com.dus.base.finder.IFinder;
import com.dus.base.schema.SEntity;
import com.dus.base.schema.SProperty;
import com.dus.context.Context;
import com.dus.impl.entity.EntityProxyHandler;

public class Session implements ISession {
	
	private final Repository repository;
	private final Store store = new Store();
	
	public Session(Repository repository) {
		this.repository = repository;
		Context.getData().setSession(this);
		Context.getData().newTransaction();
	}
	
	public Store getStore() {return store;}
	
	@Override
	public <T extends IEntity> T create(Class<T> type) {
		return create(type, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IEntity> T create(Class<T> type, IBuilder<T> builder) {
		SEntity schema = repository.getSchemaFor(type);
		
		EntityID id = new EntityID(schema, UUID.randomUUID().toString());
		EntityProxyHandler handler = new EntityProxyHandler(id, store);
		T entity = (T) handler.getEntity();
		
		Context.getData().getTransaction().newEntity(id);
		
		if(builder != null)
			for(Field field: builder.getClass().getFields()) {
				SProperty property = schema.getPropertyByName(field.getName());
				if(property == null) throw new RuntimeException("No property ("+ field.getName()+") available on schema: "+ schema.getType().getName());
				
				Object value = ReflectionHelper.getValue(field, builder);
				entity.rSet(property, value);
			}
		
		return entity;
	}

	@Override
	public <F extends IFinder<?>> F find(Class<F> finder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <F extends IFinder<?>> F find(Class<F> finder, FetchConfig fConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commit() {
		if(Context.getData().getTransaction().commit())
			Context.getData().newTransaction();
	}

	@Override
	public void revert() {
		Context.getData().getTransaction().revert();	//revert all changes in the model
		Context.getData().newTransaction();
	}

}
