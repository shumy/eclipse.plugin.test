package test.my;

import test.domain.Entity;

public interface ISession {
	void persist(Entity entity);
	void delete(Entity entity);
	
	void commit();
	void rollback();
	
	void close();
}
