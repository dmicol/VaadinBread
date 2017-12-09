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
public class UserFilter {

    private String name;

    @Past
    private LocalDate birthDateFrom;

    @Past
    private LocalDate birthDateTo;

    private Integer phoneNumber;

    private String email;

    private Boolean active = true;
    
    private Group mainGroup;

    private Gender gender;
    
    
    public void clear() {
    	name = null;
    	birthDateFrom = null;
    	birthDateTo = null;
    	phoneNumber = null;
    	email = null;
    	active = null;
    	mainGroup = null;
    	gender = null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDateFrom() {
		return birthDateFrom;
	}

	public void setBirthDateFrom(LocalDate birthDateFrom) {
		this.birthDateFrom = birthDateFrom;
	}

	public LocalDate getBirthDateTo() {
		return birthDateTo;
	}

	public void setBirthDateTo(LocalDate birthDateTo) {
		this.birthDateTo = birthDateTo;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Group getMainGroup() {
		return mainGroup;
	}

	public void setMainGroup(Group mainGroup) {
		this.mainGroup = mainGroup;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
