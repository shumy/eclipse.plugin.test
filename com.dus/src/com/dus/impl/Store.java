package com.dus.impl;

import java.util.HashMap;
import java.util.Map;

import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.schema.SProperty;
import com.dus.impl.container.Tree;
import com.dus.list.IList;

public class Store {
	
	//all data in the session
	private final Map<EntityID, IEntity> entities = new HashMap<EntityID, IEntity>();
	
	private final Tree<EntityID, SProperty, Object> properties = new Tree<EntityID, SProperty, Object>();
	private final Tree<EntityID, SProperty, IList<IEntity>> references = new Tree<EntityID, SProperty, IList<IEntity>>();
	
	public IEntity getEntity(EntityID id) {
		return entities.get(id);
	}
	
	public void mapEntity(EntityID id, IEntity entity) {
		entities.put(id, entity);
	}
	
	public Object getProperty(EntityID id, SProperty property) {
		return properties.get(id, property);
	}
	
	public void setProperty(EntityID id, SProperty property, Object newValue) {
		properties.set(id, property, newValue);
	}
	
	public void removeReference(EntityID id, SProperty property, int index) {
		
	}
	
	public void addReference(EntityID id, SProperty property, EntityID ref, int index) {
		
	}
}
