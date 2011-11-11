package com.dus.impl.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import test.domain.Entity;

import com.dus.impl.Repository;
import com.dus.impl.Session;
import com.dus.query.IQueryLoader;
import com.dus.query.LoadConfigs;
import com.dus.spi.EntityID;
import com.dus.spi.ITree;
import com.dus.spi.query.IQueryResponse;
import com.dus.spi.query.IQueryRequest.QProtocol;

public abstract class AbstractQuery<T extends Entity> implements IQueryLoader<T> {
	
	protected final Session session;
	protected final Repository repository;
	protected final QueryData queryData;
	
	private IQueryResponse qResponse;
	private HashMap<EntityID, Entity> constructed;
	
	public AbstractQuery(Session session, Repository repository, EntityID resultType, QProtocol protocol) {
		this.session = session;
		this.repository = repository;
		this.queryData = new QueryData(protocol, resultType);
	}
	
	@Override
	public void setLoadConfigs(LoadConfigs loadConfigs) {
		queryData.setLoadConfigs(loadConfigs);
	}
	
	@Override
	@SuppressWarnings({"unchecked"})
	public List<T> execute() {
		compile();
		
		qResponse = session.getQueryRouter().execute(queryData);
		constructed = new HashMap<EntityID, Entity>();
		
		ArrayList<T> results = new ArrayList<T>(qResponse.getResults().size());
		for(EntityID entityId: qResponse.getResults()) {
			Entity entity = constructEntity(entityId);
			results.add((T)entity);
		}
		
		return results;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Entity constructEntity(EntityID entityId) {
		//first search in the constructed list:
		Entity entity = constructed.get(entityId);
		if(entity != null) return entity;
		
		EClass type = repository.getEClass(entityId.type);
		
		entity = repository.create(type);
		entity.setId(entityId.typeId);
		
		ITree<EntityID, String, Object> values = qResponse.getValues();
		
		for(String field: values.keySetLevel2(entityId)) {
			EStructuralFeature feature = type.getEStructuralFeature(field);
			if(feature == null) throw new RuntimeException("Query field (" + field + ") returned by server not existent in Entity: " +  entityId.type);
			
			Object value = values.get(entityId, field);
			if(feature.isMany()) { //is a reference
				Collection<EntityID> references = (Collection<EntityID>) value;
				for(EntityID refEntityId: references) {
					Entity refEntity = constructEntity(refEntityId);
					((EList)entity.eGet(feature)).add(refEntity);
				}
			} else {
				entity.eSet(feature, value);
			}
		}
		
		session.activateAdapter(entity);
		constructed.put(entityId, entity);
		
		return entity;
	}
	
	
	protected abstract void compile();
	
}
