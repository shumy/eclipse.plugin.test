package com.dus.impl.query;

import test.domain.Entity;

import com.dus.impl.Repository;
import com.dus.impl.Session;
import com.dus.query.IQuery;
import com.dus.spi.EntityID;
import com.dus.spi.query.IQueryRequest.QProtocol;

public class QueryByFilter<T extends Entity> extends AbstractQuery<T> implements IQuery<T> {
	
	private int parameterNumber;
	
	public QueryByFilter(Session session, Repository repository, EntityID resultType, String filter) {
		super(session, repository, resultType, QProtocol.Q_FILTER);
		
		//TODO: compile queryText
		parameterNumber = 0;
	}
	


	@Override
	public void setParameter(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameter(int index, Object value) {
		//if(index < parameterNumber)
		//	queryData.setParameter(index, value);
	}
	
	@Override
	protected void compile() {
		// TODO Auto-generated method stub	
	}
}
