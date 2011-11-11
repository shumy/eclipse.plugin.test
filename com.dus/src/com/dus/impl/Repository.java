package com.dus.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.dus.base.IEntity;
import com.dus.base.schema.SEntity;
import com.dus.impl.ReflectionHelper.MethodType;
import com.dus.impl.entity.schema.SEntityImpl;
import com.dus.impl.entity.schema.SPropertyImpl;
import com.dus.list.IRList;

public class Repository {
		
	//entity schemas
	private final Map<Class<?>, SEntity> schemas = new HashMap<Class<?>, SEntity>();
	
	public void register(Class<? extends IEntity> type) {
		SEntityImpl entity = getOrRegister(type);
		
		for(Class<?> i_nterface: type.getInterfaces())
			if(i_nterface != IEntity.class) {
				SEntity iEntity = getOrRegister(i_nterface);
				entity.addComponent(iEntity);
			}
		
		schemas.put(type, entity);
		System.out.println(entity);
	}
	
	public SEntity getSchemaFor(Class<? extends IEntity> type) {
		SEntity schema = schemas.get(type);
		if(schema == null) throw new RuntimeException("No schema found for: " + type.getName());
		
		return schemas.get(type);
	}
	
	private SEntityImpl getOrRegister(Class<?> i_nterface) {
		SEntityImpl rSchema = (SEntityImpl) schemas.get(i_nterface);
		if(rSchema == null) {
			rSchema = new SEntityImpl(i_nterface);
			for(Method method: i_nterface.getDeclaredMethods()) {
				MethodType mType = ReflectionHelper.getPropertyMethodType(method.getName());
				if(mType == MethodType.GET || mType == MethodType.IS) {
					String propName = ReflectionHelper.getPropertyName(mType, method.getName());
					
					boolean isMany = false;
					if(mType == MethodType.GET && IRList.class.isAssignableFrom(method.getReturnType()))
						isMany = true;
					
					SPropertyImpl property = new SPropertyImpl(propName, isMany);
					rSchema.addProperty(property);
				}
			}
			schemas.put(i_nterface, rSchema);
		}
		
		return rSchema;
	}
}
