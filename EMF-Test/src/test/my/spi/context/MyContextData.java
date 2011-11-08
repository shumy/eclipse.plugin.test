package test.my.spi.context;

import test.my.impl.repository.MySession;
import test.my.impl.transaction.MyTransaction;

public class MyContextData {
	private MyTransaction tx;
	
	public MyTransaction getTransaction() {return tx;}
	public void newTransaction(MySession session) {this.tx = new MyTransaction(session);}
}
