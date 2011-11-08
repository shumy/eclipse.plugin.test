package com.dus.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import test.domain.Entity;

import com.dus.IRepository;
import com.dus.ISession;
import com.dus.query.IQuery;
import com.dus.query.IQueryByExample;
import com.dus.query.IQueryByFilter;
import com.dus.query.LoadConfigs;

public class Repository implements IRepository {
	
	private final Session session;
	private final EPackage ePackage;
	private final ExtendedMetaData modelMetaData;
	
	public Repository(ISession session, EPackage ePackage) {
		this.session = (Session) session;
		this.ePackage = ePackage;
		this.modelMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
	}

	private EClass getFromType(Class<?> entityType) {
		return (EClass) modelMetaData.getType(ePackage.getNsURI(), entityType.getSimpleName());
	}
	
	private Entity createRawEntity(Class<?> entityType, String id) {
		EClass eClass = getFromType(entityType);
		Entity entity = (Entity) ePackage.getEFactoryInstance().create(eClass);
		entity.setId(id);
		
		return entity;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Entity> T findById(Class<T> resultType, String id) {
		Entity entity = createRawEntity(resultType, id);
		//TODO: load entity data from server 
		session.setupAdapter(entity);
		return (T) entity;
	}

	@Override
	public <T extends Entity> T findById(Class<T> resultType, String id, LoadConfigs<T> loadConfigs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType, LoadConfigs<T> loadConfigs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> IQueryByFilter<T> qByFilter(Class<T> resultType, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> IQueryByFilter<T> qByFilter(Class<T> resultType, String filter, LoadConfigs<T> loadConfigs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQuery<Entity> qBySearch(String searchText) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
