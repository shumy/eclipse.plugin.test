package com.dus;

import com.dus.impl.Session;
import com.dus.impl.router.QueryDummyRouter;
import com.dus.impl.router.TransactionDummyRouter;

public enum Dus {
	INSTANCE;
	
	public ISession newSession() {
		return new Session(new TransactionDummyRouter(), new QueryDummyRouter());
	}
}
