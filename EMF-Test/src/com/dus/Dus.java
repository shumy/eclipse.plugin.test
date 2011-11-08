package com.dus;

import com.dus.impl.Session;
import com.dus.impl.router.DummyRouter;

public enum Dus {
	INSTANCE;
	
	public ISession newSession() {
		return new Session(new DummyRouter());
	}
}
