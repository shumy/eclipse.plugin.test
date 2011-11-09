package com.dus.spi.transaction;

import java.util.Set;

import com.dus.spi.EntityID;
import com.dus.spi.ITree;

public interface ITransactionRequest {
	interface IAddRemoveReport {
		Set<EntityID> getAddEntities();
		Set<EntityID> getRemoveEntities();
	}
	
	String getId();
	
	Set<EntityID> getNewEntities();
	Set<EntityID> getDeleteEntities();
	
	ITree<EntityID, String, Object> getProperties();
	ITree<EntityID, String, IAddRemoveReport> getReferences();
}
