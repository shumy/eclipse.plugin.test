package plugintest.dataprovider;

import plugintest.model.Person;

public enum PersonData {
	INNSTANCE;
	
	private final Person[] persons = new Person[] {
			new Person("Micael Pedrosa", "Aveiro"),
			new Person("José Castelo", "Lisboa"),
			new Person("Maria Albertina", "Lisboa"),
			new Person("Ana Branco", "Porto")
	};
	
	public Person[] getPersons() {return persons;}
}
