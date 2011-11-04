package test.my;

import test.domain.Address;
import test.domain.DomainFactory;
import test.domain.User;
import test.my.repository.MySession;

public class MyTest {

	public static MySession openSession(String repoName) {
		return new MySession();
	}
	
	public static void main(String[] args) {
		DomainFactory dFactory = DomainFactory.eINSTANCE;
		
		MySession session = openSession("demo");
		try {

				User user = dFactory.createUser();
				user.setName("Micael");
				user.setPass("password");
				
				session.persist(user);
			session.commit();
			
				Address address = dFactory.createAddress();
				address.setLocal("Aveiro");
				
				user.getAddresses().add(address);
				user.setName("Pedrosa 1");
				user.setName("Pedrosa 2");
			session.commit();
			
				user.getAddresses().remove(address);
			session.rollback();
			
			System.out.println("USER: " + user.getName());
			for(Address add: user.getAddresses()) {
				System.out.println("  ADDRESS: " + add.getLocal());
			}
			
		} finally {
			session.close();
		}
	}

}
