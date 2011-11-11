package test;

import com.dus.Dus;
import com.dus.ISession;
import com.dus.base.schema.SEntity;
import com.dus.base.schema.SProperty;

import test.domain.Group;
import test.domain.Person;
import test.domain.User;

public class MainTest {

	public static void main(String[] args) {
		ISession session = Dus.createSession();
		
			Group group1 = new Group.Builder("Admin").build();
	
			Group group2 = new Group.Builder("Researcher").build();
			
			Person user = new Person.Builder("shumy").build();
			user.setName("Micael Pedrosa")
				.setPassword("password");
			
			user.getGroups().add(group1);
			user.getGroups().add(group2);
		
		session.commit();
		
		//reflection usage:
		SEntity schema = user.getId().schema;
		SProperty property = schema.getPropertyByName("login");
		String login = user.rGet(property);
		System.out.println("LOGIN: " + login);
		
		//finder usage:
		User user1 = session.find(User.Find.class).byId("id");
		
		System.out.println("USER GROUPS: ");
		for(Group group: user1.getGroups()) {
			System.out.println(group);
		}
	}

}
