package com.dus.context;

import com.dus.ISession;
import com.dus.impl.Session;
import com.dus.impl.transaction.Transaction;

public class ContextData {
	private Transaction tx;
	private Session session;
	
	public Transaction getTransaction() {return tx;}
	public void newTransaction() {
		this.tx = new Transaction(session);
	}
	
	public ISession getSession() {return session;}
	public void setSession(ISession session) {
		this.session = (Session) session;
	}
}
