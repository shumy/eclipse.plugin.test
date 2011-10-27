package plugintest.dataprovider;

import plugintest.model.Person;

public enum PersonData {
	INNSTANCE;
	
	private final Person[] persons = new Person[] {
			new Person("Micael Pedrosa", "Aveiro"),
			new Person("José Castelo", "Lisboa"),
			new Person("Maria Albertina", "Lisboa"),
			new Person("Ana Branco", "Porto"),
			new Person("Miguel Branco", "Porto"),
			new Person("Orlando Preto", "Beja"),
			new Person("Luis Alves", "Aveiro"),
			new Person("Eduardo Miguel", "Coimbra"),
			new Person("Bruno Alexandre Cardoso", "Coimbra")
	};
	
	public Person[] getPersons() {return persons;}
}
