package com.dus;

import com.dus.query.IQuery;
import com.dus.query.IQueryByExample;
import com.dus.query.IQueryLoader;
import com.dus.query.LoadConfigs;

import test.domain.Entity;

public interface IRepository {
	
	<T extends Entity> T findById(Class<T> resultType, String id);
	<T extends Entity> T findById(Class<T> resultType, String id, LoadConfigs loadConfigs);
	
	<T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType);
	
	<T extends Entity> IQuery<T> qByFilter(Class<T> resultType, String filter);
	
	IQueryLoader<Entity> qBySearch(String searchText);
}
