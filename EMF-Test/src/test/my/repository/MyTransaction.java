package test.my.repository;

import java.util.Stack;
import java.util.UUID;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import test.domain.Entity;
import test.my.repository.MyTransactionData.AddRemoveReport;

public class MyTransaction {
	
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
	
	private final MySession session;
	private final String txId = UUID.randomUUID().toString();
	
	private final Stack<Change> changeStack = new Stack<Change>();
	private final MyTransactionData changeResume = new MyTransactionData();
	
	MyTransaction(MySession session) {
		this.session = session;
	}
	
	
	String getId() {return txId;}
	
	@SuppressWarnings("unchecked")
	void newEntity(Entity entity) {
		changeResume.newEntity(entity.getId());
		
		for(EStructuralFeature feature: entity.eClass().getEAllStructuralFeatures()) {
			if(feature.isMany()) { //is a reference
				EList<Object> values = ((EList<Object>)entity.eGet(feature));
				for(Object newValue: values) {
					AddRemoveReport arReport = changeResume.getOrCreateReport(entity.getId(), feature.getName());
					arReport.reportAdd(((Entity)newValue).getId());
				}
				
			} else if(!feature.getName().equals("id")) {
				Object newValue = entity.eGet(feature);
				if(newValue != null)
					changeResume.addProperty(entity.getId(), feature.getName(), newValue);
			}
		}
	}
	
	void deleteEntity(Entity entity) {
		changeResume.deleteEntity(entity.getId());
	}
	
	void addChange(Entity entity, EStructuralFeature feature, int type, Object oldValue, Object newValue, int listIndex) {
		
		AddRemoveReport arReport = null;
		switch (type) {
			case Notification.SET:
				if(newValue == null)
					changeResume.removeProperty(entity.getId(), feature.getName());
				else
					changeResume.addProperty(entity.getId(), feature.getName(), newValue);
				break;
			
			case Notification.UNSET:
				changeResume.removeProperty(entity.getId(), feature.getName());
				break;
				
			case Notification.ADD:
				arReport = changeResume.getOrCreateReport(entity.getId(), feature.getName());
				
				Entity refEntity1 = ((Entity)newValue);
				session.persist(refEntity1);
				
				arReport.reportAdd(refEntity1.getId());
				break;
				
			case Notification.REMOVE:
				arReport = changeResume.getOrCreateReport(entity.getId(), feature.getName());
				
				Entity refEntity2 = ((Entity)oldValue);
				session.persist(refEntity2);
				
				arReport.reportRemove(refEntity2.getId());
				break;
		}
		
		changeStack.push(new Change(type, entity, feature, oldValue, listIndex));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void revert() {
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
	
	MyTransactionData getChangeResume() {return changeResume;}
}
