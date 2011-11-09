package com.dus.test;

import java.util.List;

import com.dus.IRepository;
import com.dus.ISession;
import com.dus.Dus;
import com.dus.query.IQuery;

import test.domain.Address;
import test.domain.DomainFactory;
import test.domain.DomainPackage;
import test.domain.User;

public class MyTest {
	
	public static void main(String[] args) {
		DomainFactory dFactory = DomainFactory.eINSTANCE;
		//DomainPackage dPackage = DomainPackage.eINSTANCE;
		
		ISession session = Dus.INSTANCE.newSession();
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
			
			IRepository domainRepo = session.getRepository(DomainPackage.eINSTANCE);
			User xUser = domainRepo.findById(User.class, "xxx");
			System.out.println(xUser);
			
			IQuery<User> filter = domainRepo.qByFilter(User.class, "name = :name");
			filter.setParameter("name", "Micael");
			List<User> results = filter.execute();
			
			System.out.println("RESULTS:");
			for(User rUser: results) {
				System.out.println("  USER: " + rUser);
				System.out.print("    ADDRESS: {");
				for(Address add: rUser.getAddresses()) {
					System.out.print("[" + add.getId() + ", " + add.getLocal() + "]");
					System.out.print(", ");
				}
				System.out.println("}");
				
			}
			
		} finally {
			session.close();
		}
	}

}
