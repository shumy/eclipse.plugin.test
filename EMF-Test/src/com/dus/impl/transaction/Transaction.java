package com.dus.impl.transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.dus.ISession;
import com.dus.impl.Session;
import com.dus.impl.transaction.TransactionData.AddRemoveReport;
import com.dus.spi.EntityID;
import com.dus.spi.transaction.ITransactionResponse;

import test.domain.Entity;

public class Transaction {
	
	public static class Change {
		public final int type;
		public final Entity entity;
		public final EStructuralFeature feature;
		
		public final Object oldValue;
		public final int listIndex;		//used for EList when type is ADD or REMOVE
		
		public Change(int type, Entity entity, EStructuralFeature feature, Object oldValue, int listIndex) {
			this.type = type;
			this.entity = entity;
			this.feature = feature;
			this.oldValue = oldValue;
			this.listIndex = listIndex;
		}
		
		@Override
		public String toString() {
			String sType = "";
			switch (type) {
				case Notification.SET: sType = "SET"; break;
				case Notification.UNSET: sType = "UNSET"; break;
				case Notification.ADD: sType = "ADD"; break;
				case Notification.REMOVE: sType = "REMOVE"; break;
			}
			
			return "Type=" + sType +
			", Entity=" + entity.eClass().getName() + "@" + entity.hashCode() +
			", Field=" + feature.getName() +
			", OldValue=" + oldValue;
		}
	}
	
	private final Session session;
	
	private final Stack<Change> changeStack = new Stack<Change>();
	private final TransactionData changeResume = new TransactionData();
	
	//<client-id> <entity>
	private final HashMap<EntityID, Entity> newEntities = new HashMap<EntityID, Entity>();
	
	//<server-id> <entity>
	private final HashMap<EntityID, Entity> entities = new HashMap<EntityID, Entity>();
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public Transaction(ISession session) {
		this.session = (Session) session;
	}
	
	public TransactionData getChangeResume() {return changeResume;}
	
	public boolean commit() {
		ITransactionResponse txResponse = session.getTransactionRouter().commit(changeResume);
		
		//update tmp-id's with values from server...
		Map<EntityID, EntityID> idMap = txResponse.getIdMap();
		for(EntityID tmpId: idMap.keySet()) {
			Entity tmpEntity = newEntities.get(tmpId);
			if(tmpEntity != null) {
				session.deactivateAdapter(tmpEntity);
				tmpEntity.setId(idMap.get(tmpId).typeId);
				session.activateAdapter(tmpEntity);
				
				entities.put(idMap.get(tmpId), tmpEntity);
			}
				
		}
		
		//TODO: update entity fields with data from server...
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void newEntity(Entity entity) {
		EntityID entityId = new EntityID(entity.eClass(), entity.getId());
		
		newEntities.put(entityId, entity);
		changeResume.newEntity(entityId);
		
		for(EStructuralFeature feature: entity.eClass().getEAllStructuralFeatures()) {
			if(feature.isTransient()) continue;
			
			if(feature.isMany()) { //is a reference
				EList<Object> values = ((EList<Object>)entity.eGet(feature));
				for(Object newValue: values)
					addReport(entity, feature, newValue);
				
			} else if(!feature.getName().equals("id")) {
				changeResume.addProperty(entityId, feature.getName(), entity.eGet(feature));
			}
		}
	}
	
	public void deleteEntity(Entity entity) {
		EntityID entityId = new EntityID(entity.eClass(), entity.getId());
		
		newEntities.remove(entityId);
		changeResume.deleteEntity(entityId);
	}
	
	public void addChange(Entity entity, EStructuralFeature feature, int type, Object oldValue, Object newValue, int listIndex) {
		if(feature.isTransient()) return;
		EntityID entityId = new EntityID(entity.eClass(), entity.getId());
		
		switch (type) {
			case Notification.SET:
				changeResume.addProperty(entityId, feature.getName(), newValue);
				break;
			
			case Notification.UNSET:
				changeResume.removeProperty(entityId, feature.getName());
				break;
				
			case Notification.ADD:
				addReport(entity, feature, newValue);
				break;
				
			case Notification.REMOVE:
				removeReport(entity, feature, oldValue);
				break;
		}
		
		changeStack.push(new Change(type, entity, feature, oldValue, listIndex));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void revert() {
		while(!changeStack.isEmpty()) {
			Change ch = changeStack.pop();
			
			session.deactivateAdapter(ch.entity);
			switch (ch.type) {
				case Notification.SET:
				case Notification.UNSET:
					ch.entity.eSet(ch.feature, ch.oldValue);
					break;
				
				case Notification.ADD:
					((EList)ch.entity.eGet(ch.feature)).remove(ch.listIndex);
					break;
					
				case Notification.REMOVE:
					((EList)ch.entity.eGet(ch.feature)).add(ch.listIndex, ch.oldValue);
					break;
			}
			session.activateAdapter(ch.entity);
		}
	}

	private void addReport(Entity entity, EStructuralFeature feature, Object value) {
		EntityID entityId = new EntityID(entity.eClass(), entity.getId());
		
		AddRemoveReport arReport = changeResume.getOrCreateReport(entityId, feature.getName());
		
		Entity refEntity = ((Entity)value);
		session.persist(refEntity);	//always cascade persist for safe (maybe the entity is a new one!)
		
		EntityID refEntityId = new EntityID(refEntity.eClass(), refEntity.getId());
		
		arReport.reportAdd(refEntityId);
	}
	
	private void removeReport(Entity entity, EStructuralFeature feature, Object value) {
		EntityID entityId = new EntityID(entity.eClass(), entity.getId());
		
		AddRemoveReport arReport = changeResume.getOrCreateReport(entityId, feature.getName());
		
		Entity refEntity = ((Entity)value);
		EntityID refEntityId = new EntityID(refEntity.eClass(), refEntity.getId());
		
		arReport.reportRemove(refEntityId);
	}
}
