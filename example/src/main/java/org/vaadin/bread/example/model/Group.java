package org.vaadin.bread.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Alejandro Duarte.
 */
@Entity
@Table(name="group_")
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Boolean admin;

    @OneToMany(mappedBy="mainGroup")
    private List<User> mainUsers = new ArrayList<>();

    @ManyToMany(mappedBy="groups")
    private List<User> otherUsers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    public List<User> getMainUsers() {
    	return mainUsers;
    }
    
    public void setMainUsers(List<User> users) {
    	this.mainUsers = users;
    }
    
    
    public User addMainUser(User user) {
    	getMainUsers().add(user);
    	user.setMainGroup(this);
    	
    	return user;
    }
    
    public User removeMainUser(User user) {
    	getMainUsers().remove(user);
    	user.setMainGroup(null);
    	
    	return user;
    }
    

    public List<User> getOtherUsers() {
        return mainUsers;
    }

    public void setOtherUsers(List<User> users) {
        this.mainUsers = users;
    }
    

	public User addOtherUser(User otherUser) {
		getOtherUsers().add(otherUser);
		otherUser.addGroup(this);

		return otherUser;
	}

	public User removeOtherUser(User otherUser) {
		getOtherUsers().remove(otherUser);
		otherUser.removeGroup(this);

		return otherUser;
	}
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	
    	Group group = (Group) o;
    	
    	return id != null ? id.equals(group.id) : group.id == null;
    }
    
    @Override
    public int hashCode() {
    	return id != null ? id.hashCode() : 0;
    }

}
