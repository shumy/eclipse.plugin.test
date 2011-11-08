package com.dus;

import com.dus.query.IQuery;
import com.dus.query.IQueryByExample;
import com.dus.query.IQueryByFilter;
import com.dus.query.LoadConfigs;

import test.domain.Entity;

public interface IRepository {
	
	<T extends Entity> T findById(Class<T> resultType, String id);
	<T extends Entity> T findById(Class<T> resultType, String id, LoadConfigs<T> loadConfigs);
	
	<T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType);
	<T extends Entity> IQueryByExample<T> qByExample(Class<T> resultType, LoadConfigs<T> loadConfigs);
	
	<T extends Entity> IQueryByFilter<T> qByFilter(Class<T> resultType, String filter);
	<T extends Entity> IQueryByFilter<T> qByFilter(Class<T> resultType, String filter, LoadConfigs<T> loadConfigs);

	IQuery<Entity> qBySearch(String searchText);
}
