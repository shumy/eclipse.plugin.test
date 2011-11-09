package com.dus.impl.query;

import test.domain.Entity;

import com.dus.impl.Repository;
import com.dus.impl.Session;
import com.dus.query.IQueryByExample;
import com.dus.spi.EntityID;
import com.dus.spi.query.IQueryRequest.QProtocol;

public class QueryByExample<T extends Entity> extends AbstractQuery<T> implements IQueryByExample<T> {

	private final int parameterNumber;
	
	public QueryByExample(Session session, Repository repository, EntityID resultType) {
		super(session, repository, resultType, QProtocol.Q_FILTER);
		
		//TODO: compile queryText from resultType
		parameterNumber = 0;

	}
	
	@Override
	public void setExample(Entity example) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void compile() {
		// TODO Auto-generated method stub	
	}
}
