package com.dus;


import org.eclipse.emf.ecore.EPackage;

import test.domain.Entity;

public interface ISession {
	IRepository getRepository(EPackage ePackage);
	
	void persist(Entity entity);
	void delete(Entity entity);
	
	void commit();
	void rollback();
	
	void close();
}
