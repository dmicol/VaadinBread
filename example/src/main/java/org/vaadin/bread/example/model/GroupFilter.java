/**
 * 
 */
package org.vaadin.bread.example.model;

import java.time.LocalDate;

import javax.validation.constraints.Past;

/**
 * @author Dmitrij Colautti
 *
 */
public class GroupFilter {

    private String name;

    private Boolean admin;
    
    
    public void clear() {
    	name = null;
    	admin = null;
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

}
