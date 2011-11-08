package test.my;

import test.my.impl.repository.MySession;
import test.my.impl.router.MyRouter;

public enum MyServer {
	INSTANCE;
	
	public ISession newSession() {
		return new MySession(new MyRouter());
	}
}
