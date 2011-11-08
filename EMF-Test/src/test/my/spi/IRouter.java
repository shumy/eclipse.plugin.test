package test.my.spi;

import test.my.impl.transaction.MyTransactionData;
import test.my.impl.transaction.MyTransactionResponse;

public interface IRouter {
	MyTransactionResponse commit(MyTransactionData txData);
}
