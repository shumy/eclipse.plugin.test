package com.dus.spi.transaction;

import java.util.Map;

public interface ITransactionResponse {
	String getId();
	
	Map<String, String> getIdMap();
}
