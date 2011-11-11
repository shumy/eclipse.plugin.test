package com.dus.impl.router;

import java.util.HashMap;
import java.util.Map;

import com.dus.spi.EntityID;
import com.dus.spi.router.ITransactionRouter;
import com.dus.spi.transaction.ITransactionRequest;
import com.dus.spi.transaction.ITransactionResponse;
import com.dus.spi.transaction.TransactionResponse;

public class TransactionDummyRouter implements ITransactionRouter {
	
	@Override
	public ITransactionResponse commit(ITransactionRequest txRequest) {
		//System.out.println("COMMIT: ");
		//System.out.println(txRequest);
		
		Map<EntityID, EntityID> idMap = new HashMap<EntityID, EntityID>();
		for(EntityID id: txRequest.getNewEntities()) {
			idMap.put(id, new EntityID(id.type, id.typeId.substring(3)));
		}

		return new TransactionResponse(txRequest.getId(), idMap);
	}
}
