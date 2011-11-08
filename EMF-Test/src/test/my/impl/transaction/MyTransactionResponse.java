package test.my.impl.transaction;

import java.util.HashMap;
import java.util.Map;

public class MyTransactionResponse {
	
	//<tmp-id> <server-id>
	private final HashMap<String, String> idMap = new HashMap<String, String>();
	
	public Map<String, String> getIdMap() {return idMap;}
}
