package com.dus.spi.transaction;

import java.util.HashMap;
import java.util.Map;


public class TransactionResponse implements ITransactionResponse {
	private final String id;
	
	//<tmp-id> <server-id>
	private final HashMap<String, String> idMap = new HashMap<String, String>();

	public TransactionResponse(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {return id;}
	
	@Override
	public Map<String, String> getIdMap() {return idMap;}

}
