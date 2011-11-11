package com.dus.impl.entity.schema;

import java.util.HashMap;
import java.util.Map;

import com.dus.base.schema.SEntity;
import com.dus.base.schema.SProperty;
import com.dus.base.schema.action.SAction;
import com.dus.list.IRList;

public class SEntityImpl implements SEntity {
	private final Class<?> type;
	private final Map<Class<?>, SEntity> components = new HashMap<Class<?>, SEntity>();

	private final Map<String, SProperty> properties = new HashMap<String, SProperty>();
	private final Map<String, SAction> actions = new HashMap<String, SAction>();
	
	public SEntityImpl(Class<?> type) {
		this.type = type;
	}
	
	public void addComponent(SEntity component) {
		components.put(component.getType(), component);
	}
	
	public void addProperty(SPropertyImpl property) {
		properties.put(property.getName(), property);
	}
	
	@Override
	public Class<?> getType() {return type;}

	@Override
	public IRList<SProperty> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SProperty getPropertyByName(String name) {
		return properties.get(name);
	}

	@Override
	public IRList<SAction> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SAction getActionByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SEntity: ");
		sb.append(type.getName());
		sb.append("\n");
		
		for(SProperty prop: properties.values()) {
			sb.append("  P {");
			sb.append("name=");
			sb.append(prop.getName());
			sb.append(", isMany=");
			sb.append(prop.isMany());
			sb.append("}\n");
		}
		
		return sb.toString();
	}
}
