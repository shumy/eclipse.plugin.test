package com.dus.impl.transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.dus.spi.EntityID;
import com.dus.spi.ITree;
import com.dus.spi.Tree;
import com.dus.spi.transaction.ITransactionRequest;

public final class TransactionData implements ITransactionRequest {
	
	public static class AddRemoveReport implements ITransactionRequest.IAddRemoveReport {
		//<entity-id>
		private final Set<EntityID> addEntities = new HashSet<EntityID>();
		
		//<entity-id>
		private final Set<EntityID> removeEntities = new HashSet<EntityID>();
		
		@Override
		public Set<EntityID> getAddEntities() {return Collections.unmodifiableSet(addEntities);}
		
		@Override
		public Set<EntityID> getRemoveEntities() {return Collections.unmodifiableSet(removeEntities);}
		
		void reportAdd(EntityID id) {
			removeEntities.remove(id);
			addEntities.add(id);
		}
		
		void reportRemove(EntityID id) {
			addEntities.remove(id);
			removeEntities.add(id);
		}
	}
	
	private final String id = EcoreUtil.generateUUID();
	
	//<entity-id>
	private final Set<EntityID> newEntities = new HashSet<EntityID>();
	
	//<entity-id>
	private final Set<EntityID> deleteEntities = new HashSet<EntityID>();
	
	//<entity-id> <property-name> <supported-value>
	private final Tree<EntityID, String, Object> properties = new Tree<EntityID, String, Object>();
	
	//<entity-id> <property-name> <add-remove-report>
	private final Tree<EntityID, String, IAddRemoveReport> references = new Tree<EntityID, String, IAddRemoveReport>();
	
	@Override
	public String getId() {return id;}
	
	@Override
	public Set<EntityID> getNewEntities() {return Collections.unmodifiableSet(newEntities);}
	
	@Override
	public Set<EntityID> getDeleteEntities() {return Collections.unmodifiableSet(deleteEntities);}
	
	@Override
	public ITree<EntityID, String, Object> getProperties() {return properties;}
	
	@Override
	public ITree<EntityID, String, IAddRemoveReport> getReferences() {return references;}
	
	void newEntity(EntityID id) {
		deleteEntities.remove(id);
		newEntities.add(id);
	}
	
	void deleteEntity(EntityID id) {
		//delete all change history:
		properties.remove(id);
		references.remove(id);
		
		if(newEntities.contains(id))
			newEntities.remove(id);
		else
			deleteEntities.add(id);
	}
	
	void addProperty(EntityID entityId, String propName, Object value) {
		if(value != null)
			properties.add(entityId, propName, value);
		else
			properties.remove(entityId, propName);
	}
	
	void removeProperty(EntityID entityId, String propName) {
		properties.remove(entityId, propName);
	}
	
	AddRemoveReport getOrCreateReport(EntityID entityId, String propName) {
		AddRemoveReport arReport = (AddRemoveReport) references.get(entityId, propName);
		if(arReport == null) {
			arReport = new AddRemoveReport();
			references.add(entityId, propName, arReport);
		}
		return arReport;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ENTITIES: {");
		if(!deleteEntities.isEmpty()) {
			for(EntityID id: deleteEntities) {
				sb.append(id);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
		}
		sb.append("}\n");
		
		sb.append("NEW ENTITIES: {");
		if(!newEntities.isEmpty()) {
			for(EntityID id: newEntities) {
				sb.append(id);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
		}
		sb.append("}\n");
		
		sb.append("PROPERTIES: \n");
		for(EntityID id: properties.keySetLevel1()) {
			sb.append("  ENTITY "); 
			sb.append(id);
			sb.append(": {");
			if(!properties.keySetLevel2(id).isEmpty()) {
				for(String prop: properties.keySetLevel2(id)) {
					sb.append(prop);
					sb.append("=");
					sb.append(properties.get(id, prop));
					sb.append(", ");
				}
				sb.delete(sb.length()-2, sb.length());
			}
			sb.append("}\n");
		}
		
		sb.append("REFERENCES: \n");
		for(EntityID id: references.keySetLevel1()) {
			sb.append("  ENTITY "); 
			sb.append(id);
			sb.append(": {");
			for(String ref: references.keySetLevel2(id)) {
				sb.append(ref);
				sb.append("=(add=[");
				IAddRemoveReport arReport = references.get(id, ref);
				if(!arReport.getAddEntities().isEmpty()) {
					for(EntityID idToAdd: arReport.getAddEntities()) {
						sb.append(idToAdd);
						sb.append(", ");
					}
					sb.delete(sb.length()-2, sb.length());
				}
				sb.append("], remove=[");
				if(!arReport.getRemoveEntities().isEmpty()) {
					for(EntityID idToRemove: arReport.getRemoveEntities()) {
						sb.append(idToRemove);
						sb.append(", ");
					}
					sb.delete(sb.length()-2, sb.length());
				}
				sb.append("]), ");
			}
			sb.delete(sb.length()-2, sb.length());
			
			sb.append("}\n");
		}
		
		
		return sb.toString();
	}
}
