/**
 * 
 */
package org.vaadin.bread.example.repo;

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

	public void clear() {
		name = null;
		birthDateFrom = null;
		birthDateTo = null;
	}
}
