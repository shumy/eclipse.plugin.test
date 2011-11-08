package com.dus.impl.transaction;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.dus.spi.Tree;
import com.dus.spi.transaction.ITransactionRequest;

public class TransactionData implements ITransactionRequest {
	
	public static class AddRemoveReport implements ITransactionRequest.IAddRemoveReport {
		//<entity-id>
		private final Set<String> addEntities = new HashSet<String>();
		
		//<entity-id>
		private final Set<String> removeEntities = new HashSet<String>();
		
		@Override
		public Set<String> getAddEntities() {return addEntities;}
		
		@Override
		public Set<String> getRemoveEntities() {return removeEntities;}
		
		void reportAdd(String id) {
			removeEntities.remove(id);
			addEntities.add(id);
		}
		
		void reportRemove(String id) {
			addEntities.remove(id);
			removeEntities.add(id);
		}
	}
	
	private final String txId = EcoreUtil.generateUUID();
	
	//<entity-id>
	private final Set<String> newEntities = new HashSet<String>();
	
	//<entity-id>
	private final Set<String> deleteEntities = new HashSet<String>();
	
	//<entity-id> <property-name> <supported-value>
	private final Tree<String, String, Object> properties = new Tree<String, String, Object>();
	
	//<entity-id> <property-name> <add-remove-report>
	private final Tree<String, String, IAddRemoveReport> references = new Tree<String, String, IAddRemoveReport>();
	
	@Override
	public String getId() {return txId;}
	
	@Override
	public Set<String> getNewEntities() {return newEntities;}
	
	@Override
	public Set<String> getDeleteEntities() {return deleteEntities;}
	
	@Override
	public Tree<String, String, Object> getProperties() {return properties;}
	
	@Override
	public Tree<String, String, IAddRemoveReport> getReferences() {return references;}
	
	void newEntity(String id) {
		deleteEntities.remove(id);
		newEntities.add(id);
	}
	
	void deleteEntity(String id) {
		//delete all change history:
		properties.remove(id);
		references.remove(id);
		
		if(newEntities.contains(id))
			newEntities.remove(id);
		else
			deleteEntities.add(id);
	}
	
	void addProperty(String entityId, String propName, Object value) {
		if(value != null)
			properties.add(entityId, propName, value);
		else
			properties.remove(entityId, propName);
	}
	
	void removeProperty(String entityId, String propName) {
		properties.remove(entityId, propName);
	}
	
	AddRemoveReport getOrCreateReport(String entityId, String propName) {
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
			for(String id: deleteEntities) {
				sb.append(id);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
		}
		sb.append("}\n");
		
		sb.append("NEW ENTITIES: {");
		if(!newEntities.isEmpty()) {
			for(String id: newEntities) {
				sb.append(id);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
		}
		sb.append("}\n");
		
		sb.append("PROPERTIES: \n");
		for(String id: properties.keySetLevel1()) {
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
		for(String id: references.keySetLevel1()) {
			sb.append("  ENTITY "); 
			sb.append(id);
			sb.append(": {");
			for(String ref: references.keySetLevel2(id)) {
				sb.append(ref);
				sb.append("=(add=[");
				IAddRemoveReport arReport = references.get(id, ref);
				if(!arReport.getAddEntities().isEmpty()) {
					for(String idToAdd: arReport.getAddEntities()) {
						sb.append(idToAdd);
						sb.append(", ");
					}
					sb.delete(sb.length()-2, sb.length());
				}
				sb.append("], remove=[");
				if(!arReport.getRemoveEntities().isEmpty()) {
					for(String idToRemove: arReport.getRemoveEntities()) {
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
