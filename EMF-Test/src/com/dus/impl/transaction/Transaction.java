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
import com.dus.spi.router.ITransactionRouter;
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
	private final HashMap<String, Entity> newEntities = new HashMap<String, Entity>();
	
	//<server-id> <entity>
	private final HashMap<String, Entity> entities = new HashMap<String, Entity>();
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public Transaction(ISession session) {
		this.session = (Session) session;
	}
	
	public TransactionData getChangeResume() {return changeResume;}
	
	public boolean commit(ITransactionRouter router) {
		ITransactionResponse txResponse = router.commit(changeResume);
		
		//update tmp-id's with values from server...
		Map<String, String> idMap = txResponse.getIdMap();
		for(String tmpId: idMap.keySet()) {
			Entity tmpEntity = newEntities.get(tmpId);
			if(tmpEntity != null) {
				tmpEntity.eSetDeliver(false);
				tmpEntity.setId(idMap.get(tmpId));
				tmpEntity.eSetDeliver(true);
				
				entities.put(tmpEntity.getId(), tmpEntity);
			}
				
		}
		
		//TODO: update entity fields with data from server...
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void newEntity(Entity entity) {
		newEntities.put(entity.getId(), entity);
		changeResume.newEntity(entity.getId());
		
		for(EStructuralFeature feature: entity.eClass().getEAllStructuralFeatures()) {
			if(feature.isTransient()) continue;
			
			if(feature.isMany()) { //is a reference
				EList<Object> values = ((EList<Object>)entity.eGet(feature));
				for(Object newValue: values)
					addReport(entity, feature, newValue);
				
			} else if(!feature.getName().equals("id")) {
				changeResume.addProperty(entity.getId(), feature.getName(), entity.eGet(feature));
			}
		}
	}
	
	public void deleteEntity(Entity entity) {
		newEntities.remove(entity.getId());
		changeResume.deleteEntity(entity.getId());
	}
	
	public void addChange(Entity entity, EStructuralFeature feature, int type, Object oldValue, Object newValue, int listIndex) {
		if(feature.isTransient()) return;
		
		switch (type) {
			case Notification.SET:
				changeResume.addProperty(entity.getId(), feature.getName(), newValue);
				break;
			
			case Notification.UNSET:
				changeResume.removeProperty(entity.getId(), feature.getName());
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
			
			ch.entity.eSetDeliver(false);
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
			ch.entity.eSetDeliver(true);
		}
	}

	private void addReport(Entity entity, EStructuralFeature feature, Object value) {
		AddRemoveReport arReport = changeResume.getOrCreateReport(entity.getId(), feature.getName());
		
		Entity refEntity = ((Entity)value);
		session.persist(refEntity);	//always cascade persist for safe (maybe the entity is a new one!)
		
		arReport.reportAdd(refEntity.getId());
	}
	
	private void removeReport(Entity entity, EStructuralFeature feature, Object value) {
		AddRemoveReport arReport = changeResume.getOrCreateReport(entity.getId(), feature.getName());
		
		Entity refEntity = ((Entity)value);
		
		arReport.reportRemove(refEntity.getId());
	}
}
