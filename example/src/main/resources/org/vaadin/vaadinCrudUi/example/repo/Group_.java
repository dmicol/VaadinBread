package org.vaadin.vaadinCrudUi.example.repo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-12-01T22:18:42.423+0100")
@StaticMetamodel(Group.class)
public class Group_ {
	public static volatile SingularAttribute<Group, Long> id;
	public static volatile SingularAttribute<Group, String> name;
	public static volatile SingularAttribute<Group, Boolean> admin;
}
