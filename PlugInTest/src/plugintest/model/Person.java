package plugintest.model;

import java.util.Date;

public class Person {
	private String name;
	private String address;
	private Date birthday;
	
	public Person(String name, String address) {
		this(name, address, null);
	}
	
	public Person(String name, String address, Date birthday) {
		this.name = name;
		this.address = address;
		this.birthday = birthday;
	}
	
	public String getFullName() {return name;}
	public String getAddress() {return address;}
	public Date getBirthday() {return birthday;}
}
