package com.dus.impl;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EStoreEObjectImpl.EStoreImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import test.domain.Entity;

import com.dus.IRepository;
import com.dus.ISession;
import com.dus.impl.query.QueryByFilter;
import com.dus.impl.query.QueryByExample;
import com.dus.impl.query.QueryById;
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
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T createEmptyEntity(Class<T> entityType) {
		EClass eClass = getEClass(entityType);
		T entity = (T) ePackage.getEFactoryInstance().create(eClass);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T create(EClass type) {
		T entity = (T) ePackage.getEFactoryInstance().create(type);
		
		InternalEObject iEntity = (InternalEObject) entity;
		iEntity.eSetStore(new EStoreImpl() {
			
			@Override
			public void add(InternalEObject eObject, EStructuralFeature feature, int index, Object value) {
				System.out.println("ADD: " + eObject.eClass().getName() + 
						" F:" + feature.getName() + "=" + value +" at " + index);
				
				super.add(eObject, feature, index, value);
			}
			
			@Override
			public Object remove(InternalEObject eObject, EStructuralFeature feature, int index) {
				System.out.println("REMOVE: " + eObject.eClass().getName() + 
						" F:" + feature.getName() + " at " +index);
				
				return super.remove(eObject, feature, index);
			}
			
			@Override
			public Object set(InternalEObject eObject, EStructuralFeature feature, int index, Object value) {
				System.out.println("SET: " + eObject.eClass().getName() + 
						" F:" + feature.getName() + "=" + value +" at " + index);
				
				return super.set(eObject, feature, index, value);
			}
			
			@Override
			public Object get(InternalEObject eObject, EStructuralFeature feature, int index) {
				System.out.println("GET: " + eObject.eClass().getName() + 
						" F:" + feature.getName() + " at " +index);
				
				return super.get(eObject, feature, index);
			}
			
		});
		
		//entity.setId("TMP"+EcoreUtil.generateUUID());
		return entity;
	}
	
	@Override
	public <T extends Entity> T create(Class<T> type) {
		EClass eClass = getEClass(type);
		return create(eClass);
	}
	
	@Override
	public <T extends Entity> T findById(Class<T> resultType, String id) {
		return findById(resultType, id, null);
	}

	@Override
	public <T extends Entity> T findById(Class<T> resultType, String id, LoadConfigs loadConfigs) {
		QueryById<T> qId = new QueryById<T>(session, this, new EntityID(resultType, id));
		qId.setLoadConfigs(loadConfigs);
		List<T> results = qId.execute();
		
		if(results.isEmpty()) return null;
		return results.get(0);
	}
	
	@Override
	public <T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType) {
		EClass type = getEClass(resultType);
		return new QueryByExample<T>(session, this, new EntityID(type, null));
	}

	@Override
	public <T extends Entity> IQuery<T> qByFilter(Class<T> resultType, String filter) {
		EClass type = getEClass(resultType);
		return new QueryByFilter<T>(session, this, new EntityID(type, null), filter);
	}
	
	@Override
	public IQueryLoader<Entity> qBySearch(String searchText) {
		// TODO Auto-generated method stub
		return null;
	}

}
