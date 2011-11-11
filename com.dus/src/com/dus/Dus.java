package com.dus;

import test.domain.Group;
import test.domain.Person;
import test.domain.User;

import com.dus.impl.Repository;
import com.dus.impl.Session;

public class Dus {
	private static final Repository repository = new Repository();
	
	static {
		//TODO: replace from config files...
		
		repository.register(Person.class);
		repository.register(User.class);
		repository.register(Group.class);
	}
	
	public static ISession createSession() {
		Session session = new Session(repository);
		return session;
	}
}
