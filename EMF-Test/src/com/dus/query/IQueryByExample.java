package com.dus.query;

import test.domain.Entity;

public interface IQueryByExample<T extends Entity> extends IQueryLoader<T> {
	void setExample(Entity example);
}
