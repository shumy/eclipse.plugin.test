package com.dus.spi.query;

import com.dus.query.LoadConfigs;
import com.dus.spi.EntityID;

public interface IQueryRequest {
	public enum QProtocol {
		Q_ID,
		Q_FILTER,
		Q_DSQL,
		Q_SEARCH,
	}
	
	String getId();
	
	QProtocol getProtocol();
	
	EntityID getResultType();
	
	String getQueryText();
	
	Object[] getParameters();
	
	LoadConfigs getLoadConfigs();
}
