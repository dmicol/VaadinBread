/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.beans.PropertyDescriptor;

public class FilterValue {

	private PropertyDescriptor property;
	private Operation operation;
	private Object value1;
	private Object value2;
	
	public PropertyDescriptor getProperty() {
		return property;
	}
	public void setProperty(PropertyDescriptor property) {
		this.property = property;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Object getValue1() {
		return value1;
	}
	public void setValue1(Object value1) {
		this.value1 = value1;
	}
	public Object getValue2() {
		return value2;
	}
	public void setValue2(Object value2) {
		this.value2 = value2;
	}
	
	
}
