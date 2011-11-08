package test.my.impl.router;

import test.my.impl.transaction.MyTransactionData;
import test.my.impl.transaction.MyTransactionResponse;
import test.my.spi.IRouter;

public class MyRouter implements IRouter {
	
	@Override
	public MyTransactionResponse commit(MyTransactionData txData) {
		System.out.println("COMMIT: ");
		System.out.println(txData);
		
		MyTransactionResponse txResponse = new MyTransactionResponse();
		for(String id: txData.getNewEntities()) {
			txResponse.getIdMap().put(id, id.substring(4));
		}
		
		return txResponse;
	}
}
