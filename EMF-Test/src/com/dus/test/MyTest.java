package com.dus.test;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.dus.IRepository;
import com.dus.ISession;
import com.dus.Dus;
import com.dus.query.IQuery;

import test.domain.Address;
import test.domain.DomainPackage;
import test.domain.User;

public class MyTest {
	
	public static void main(String[] args) {
		
		ISession session = Dus.INSTANCE.newSession();
		IRepository domainRepo = session.getRepository(DomainPackage.eINSTANCE);
		
		try {
				User user = domainRepo.create(User.class);				
				user.setName("Micael");
				user.setPass("password");
				
				session.persist(user);
			session.commit();
			
				Address address1 = domainRepo.create(Address.class);
				address1.setLocal("Aveiro");
				
				Address address2 = domainRepo.create(Address.class);
				address2.setLocal("Lisboa");
				
				Address address3 = domainRepo.create(Address.class);
				address3.setLocal("Porto");
				
				user.getAddresses().add(address1);
				user.getAddresses().add(address2);
				user.getAddresses().add(address3);
				user.setName("Pedrosa 1");
				user.setName("Pedrosa 2");
								
			session.commit();
				
				System.out.println("USER: " + user.getName());
				EList<Address> addresses = user.getAddresses();
				for(Address add: addresses) {
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
