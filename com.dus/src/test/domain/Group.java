package test.domain;

import com.dus.base.IEntity;
import com.dus.base.builder.BaseBuilder;
import com.dus.base.finder.IFinder;

public interface Group extends IEntity {

	//PROPERTIES--------------------------------------------------------------------------------
	String getName();
	Group setName(String name);
	
	//BUILDER----------------------------------------------------------------------------------
	public class Builder extends BaseBuilder<Group> {
		public final String name;
		
		public Builder(String name) {
			super(Group.class);
			this.name = name;
		}
	}
	
	//FINDER------------------------------------------------------------------------------------
	interface Find extends IFinder<User> {
	}
}
