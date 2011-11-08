package com.dus.spi.transaction;

import java.util.Set;

import com.dus.spi.Tree;

public interface ITransactionRequest {
	interface IAddRemoveReport {
		Set<String> getAddEntities();
		Set<String> getRemoveEntities();
	}
	
	String getId();
	
	Set<String> getNewEntities();
	Set<String> getDeleteEntities();
	
	Tree<String, String, Object> getProperties();
	Tree<String, String, IAddRemoveReport> getReferences();
}
