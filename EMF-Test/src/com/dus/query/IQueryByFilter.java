package com.dus.query;

import test.domain.Entity;

public interface IQueryByFilter<T extends Entity> extends IQuery<T> {

	void setParameter(String name, Object value);
	void setParameter(int index, Object value);
}
