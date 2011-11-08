package com.dus.impl.router;

import com.dus.spi.router.ITransactionRouter;
import com.dus.spi.transaction.ITransactionRequest;
import com.dus.spi.transaction.ITransactionResponse;
import com.dus.spi.transaction.TransactionResponse;

public class DummyRouter implements ITransactionRouter {
	
	@Override
	public ITransactionResponse commit(ITransactionRequest txRequest) {
		System.out.println("COMMIT: ");
		System.out.println(txRequest);
		
		TransactionResponse txResponse = new TransactionResponse(txRequest.getId());
		for(String id: txRequest.getNewEntities()) {
			txResponse.getIdMap().put(id, id.substring(3));
		}
		
		return txResponse;
	}
}
