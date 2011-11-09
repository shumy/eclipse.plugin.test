package com.dus.query;

import test.domain.Entity;

public interface IQuery<T extends Entity> extends IQueryLoader<T> {
	void setParameter(String name, Object value);
	void setParameter(int index, Object value);
}
