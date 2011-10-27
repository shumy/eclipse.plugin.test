package plugintest.dataprovider;

import java.util.Calendar;

import org.myWidgets.helper.DateHelper;

import plugintest.model.Person;

public enum PersonData {
	INNSTANCE;
		
	private final Person[] persons = new Person[] {
			new Person("Micael Pedrosa", "Aveiro", DateHelper.INSTANCE.createDate(2011, Calendar.JANUARY, 1)),
			new Person("José Castelo", "Lisboa"),
			new Person("Maria Albertina", "Lisboa"),
			new Person("Ana Branco", "Porto"),
			new Person("Miguel Branco", "Porto", DateHelper.INSTANCE.createDate(2010, Calendar.JANUARY, 1)),
			new Person("Orlando Preto", "Beja"),
			new Person("Luis Alves", "Aveiro"),
			new Person("Eduardo Miguel", "Coimbra", DateHelper.INSTANCE.createDate(2010, Calendar.JANUARY, 2)),
			new Person("Bruno Alexandre Cardoso", "Coimbra")
	};
	
	public Person[] getPersons() {return persons;}
}
