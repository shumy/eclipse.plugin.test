package com.dus.query;

import test.domain.Entity;

public interface IQueryByExample<T extends Entity> extends IQuery<T> {
	void setExample(T example);
}
