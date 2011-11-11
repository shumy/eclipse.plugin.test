package com.dus.impl.query;

import com.dus.impl.Repository;
import com.dus.impl.Session;
import com.dus.spi.EntityID;
import com.dus.spi.query.IQueryRequest.QProtocol;

import test.domain.Entity;

public class QueryById<T extends Entity> extends AbstractQuery<T> {

	public QueryById(Session session, Repository repository, EntityID resultType) {
		super(session, repository, resultType, QProtocol.Q_ID);
	}
	
	@Override
	protected void compile() {
		//no need for compilation
	}

}
