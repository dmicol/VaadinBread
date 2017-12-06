package org.vaadin.vaadinCrudUi.example.repo;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-12-03T11:32:53.581+0100")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, LocalDate> birthDate;
	public static volatile SingularAttribute<User, Integer> phoneNumber;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Boolean> active;
	public static volatile SingularAttribute<User, Group> mainGroup;
	public static volatile SetAttribute<User, Group> groups;
	public static volatile SingularAttribute<User, Gender> gender;
}
