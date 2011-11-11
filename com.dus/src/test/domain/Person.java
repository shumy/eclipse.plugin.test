package test.domain;

import com.dus.base.builder.BaseBuilder;

public interface Person extends User {
	//PROPERTIES--------------------------------------------------------------------------------
	String getName();
	Person setName(String name);
	
	String getAddress();
	Person setAddress(String address);
	
	//BUILDER----------------------------------------------------------------------------------
	public class Builder extends BaseBuilder<Person> {
		public final String login;
		
		public Builder(String login) {
			super(Person.class);
			this.login = login;
		}
	}
}
