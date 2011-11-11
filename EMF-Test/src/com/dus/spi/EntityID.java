package com.dus.spi;

import org.eclipse.emf.ecore.EClass;

public final class EntityID {
	public final String type;
	public final String typeId;
	
	public EntityID(EClass type, String typeId) {
		this(type.getName(), typeId);
	}
	
	public EntityID(Class<?> type, String typeId) {
		this(type.getSimpleName(), typeId);
	}
	
	public EntityID(String type, String typeId) {
		this.type = type;
		this.typeId = typeId;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ typeId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		
		if(obj instanceof EntityID) {
			EntityID other = (EntityID) obj;
			if(type.equals(other.type) && typeId.equals(other.typeId))
				return true;
			else 
				return false;
		} else 
			return false;
		
	}
	
	@Override
	public String toString() {
		return type + ":" + typeId;
	}
}
