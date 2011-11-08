package com.dus.spi.context;

import com.dus.ISession;
import com.dus.impl.transaction.Transaction;

public class ContextData {
	private Transaction tx;
	
	public Transaction getTransaction() {return tx;}
	public void newTransaction(ISession session) {this.tx = new Transaction(session);}
}
