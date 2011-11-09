package com.dus.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import test.domain.Entity;

import com.dus.IRepository;
import com.dus.ISession;
import com.dus.impl.query.Query;
import com.dus.impl.query.QueryByExample;
import com.dus.query.IQuery;
import com.dus.query.IQueryByExample;
import com.dus.query.IQueryLoader;
import com.dus.query.LoadConfigs;
import com.dus.spi.EntityID;

public class Repository implements IRepository {
	
	private final Session session;
	private final EPackage ePackage;
	private final ExtendedMetaData modelMetaData;
	
	public Repository(ISession session, EPackage ePackage) {
		this.session = (Session) session;
		this.ePackage = ePackage;
		this.modelMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
	}

	
	
	public <T extends Entity> EClass getEClass(Class<T> entityType) {
		return (EClass) modelMetaData.getType(ePackage.getNsURI(), entityType.getSimpleName());
	}
	
	public EClass getEClass(String name) {
		EClass eClass = (EClass) modelMetaData.getType(ePackage.getNsURI(), name);
		//System.out.println("Try to create: "+ name + "=" + eClass);
		return eClass;
	}
	
	public <T extends Entity> Entity createEmptyEntity(Class<T> entityType, String id) {
		EClass eClass = getEClass(entityType);
		Entity entity = (Entity) ePackage.getEFactoryInstance().create(eClass);
		entity.setId(id);
		
		return entity;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Entity> T findById(Class<T> resultType, String id) {
		Entity entity = createEmptyEntity(resultType, id);
		//TODO: load entity data from server 
		session.activateAdapter(entity);
		return (T) entity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Entity> T findById(Class<T> resultType, String id, LoadConfigs loadConfigs) {
		Entity entity = createEmptyEntity(resultType, id);
		//TODO: load entity data from server 
		session.activateAdapter(entity);
		return (T) entity;
	}
	
	@Override
	public <T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType) {
		EClass type = getEClass(resultType);
		return new QueryByExample<T>(session, this, new EntityID(type, null));
	}

	@Override
	public <T extends Entity> IQuery<T> qByFilter(Class<T> resultType, String filter) {
		EClass type = getEClass(resultType);
		return new Query<T>(session, this, new EntityID(type, null), filter);
	}
	
	@Override
	public IQueryLoader<Entity> qBySearch(String searchText) {
		// TODO Auto-generated method stub
		return null;
	}

}
