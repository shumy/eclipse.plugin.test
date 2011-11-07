package test.my.repository;

import java.util.HashSet;
import java.util.Set;

import test.my.Tree;

public class MyTransactionData {
	
	public static class AddRemoveReport {
		//<entity-id>
		private final Set<String> addEntities = new HashSet<String>();
		
		//<entity-id>
		private final Set<String> removeEntities = new HashSet<String>();
		
		public void reportAdd(String id) {
			removeEntities.remove(id);
			addEntities.add(id);
		}
		
		public void reportRemove(String id) {
			addEntities.remove(id);
			removeEntities.add(id);
		}
		
		public Set<String> getAddEntities() {return addEntities;}
		public Set<String> getRemoveEntities() {return removeEntities;}
	}
	
	//<entity-id>
	private final Set<String> newEntities = new HashSet<String>();
	
	//<entity-id>
	private final Set<String> deleteEntities = new HashSet<String>();
	
	//<entity-id> <property-name> <supported-value>
	private final Tree<String, String, Object> properties = new Tree<String, String, Object>();
	
	//<entity-id> <property-name> <add-remove-report>
	private final Tree<String, String, AddRemoveReport> references = new Tree<String, String, MyTransactionData.AddRemoveReport>();
	
	
	public void newEntity(String id) {
		deleteEntities.remove(id);
		newEntities.add(id);
	}
	
	public void deleteEntity(String id) {
		if(newEntities.contains(id)) {
			newEntities.remove(id);
			return;
		}
		deleteEntities.add(id);
	}
	
	public void addProperty(String entityId, String propName, Object value) {
		properties.add(entityId, propName, value);
	}
	
	public void removeProperty(String entityId, String propName) {
		properties.remove(entityId, propName);
	}
	
	public AddRemoveReport getOrCreateReport(String entityId, String propName) {
		AddRemoveReport arReport = references.get(entityId, propName);
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
				AddRemoveReport arReport = references.get(id, ref);
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
