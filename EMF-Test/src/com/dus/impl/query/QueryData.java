package com.dus.impl.query;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.dus.query.LoadConfigs;
import com.dus.spi.EntityID;
import com.dus.spi.query.IQueryRequest;

public final class QueryData implements IQueryRequest {
	private final String id = EcoreUtil.generateUUID();
	
	private final QProtocol qProtocol;
	private final EntityID resultType;
	
	private String queryText = "";
	private LoadConfigs loadConfigs;
	private Object[] parameters;
	
	public QueryData(QProtocol qProtocol, EntityID resultType) {
		this.qProtocol = qProtocol;
		this.resultType = resultType;
	}
	
	@Override
	public String getId() {return id;}
	
	@Override
	public QProtocol getProtocol() {return qProtocol;}
	
	@Override
	public EntityID getResultType() {return resultType;}
	
	@Override
	public String getQueryText() {return queryText;}
	
	@Override
	public Object[] getParameters() {return parameters;}
	
	@Override
	public LoadConfigs getLoadConfigs() {return loadConfigs;}
	
	void setLoadConfigs(LoadConfigs loadConfigs) {
		this.loadConfigs = loadConfigs;
	}
	
	void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PROTOCOL: ");
		sb.append(qProtocol);
		
		sb.append("\nRESULT-TYPE: ");
		sb.append(resultType.type);
		sb.append(resultType.type);
		
		sb.append("\nLOAD-CONFIGS: {");
		if(loadConfigs != null)
			if(!loadConfigs.isEmpty()) {
				for(String field: loadConfigs.getFields()) {
					sb.append(field);
					sb.append("=");
					sb.append(loadConfigs.getValue(field));
					sb.append(", ");
				}
				sb.delete(sb.length()-2, sb.length());
			}
		sb.append("}\n");

		switch (qProtocol) {
			case Q_ID:
				sb.append("ID-SEARCH: ");
				sb.append(resultType.typeId);
				break;
	
			case Q_FILTER:
				sb.append("FILTER: ");
				sb.append(queryText);
				printParameters(sb);
				break;
				
			case Q_DSQL:
				sb.append("DSQL: ");
				sb.append(queryText);
				printParameters(sb);
				break;
				
			case Q_SEARCH:
				sb.append("SEARCH: ");
				sb.append(queryText);
				break;
		}
		
		
		return sb.toString();
	}
	
	private void printParameters(StringBuilder sb) {
		sb.append("\nPARAMETERS: {");
		if(parameters != null)
			if(parameters.length != 0) {
				int index = 0;
				for(Object value: parameters) {
					sb.append(index);
					sb.append("=");
					sb.append(value);
					sb.append(", ");
					index++;
				}
				sb.delete(sb.length()-2, sb.length());
			}
		sb.append("}\n");
	}
}
