package com.dus.query;

import java.util.List;

import test.domain.Entity;

public interface IQueryLoader<T extends Entity> {
	void setLoadConfigs(LoadConfigs loadConfigs);
	
	List<T> execute();
}
