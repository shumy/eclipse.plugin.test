package com.dus.spi.transaction;

import java.util.Map;

import com.dus.spi.EntityID;

public interface ITransactionResponse {
	String getId();
	
	Map<EntityID, EntityID> getIdMap();
}
