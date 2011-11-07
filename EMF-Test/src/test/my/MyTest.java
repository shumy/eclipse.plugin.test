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
				
				Address address1 = dFactory.createAddress();
				address1.setLocal("Aveiro");
				
				Address address2 = dFactory.createAddress();
				address2.setLocal("Lisboa");
				
				Address address3 = dFactory.createAddress();
				address3.setLocal("Porto");
				
				user.getAddresses().add(address1);
				user.getAddresses().add(address2);
				user.getAddresses().add(address3);
				user.setName("Pedrosa 1");
				user.setName("Pedrosa 2");
			session.commit();
				
				System.out.println("USER: " + user.getName());
				for(Address add: user.getAddresses()) {
					System.out.println("  ADDRESS: " + add.getLocal());
				}
				System.out.println("");
			
				user.getAddresses().remove(address2);
				user.getAddresses().remove(address1);
				user.getAddresses().remove(address3);
			session.commit();
			
				System.out.println("USER: " + user.getName());
				for(Address add: user.getAddresses()) {
					System.out.println("  ADDRESS: " + add.getLocal());
				}
				System.out.println("");
			
				user.setPass("new pass");
				session.delete(user);
			session.commit();
			
		} finally {
			session.close();
		}
	}

}
