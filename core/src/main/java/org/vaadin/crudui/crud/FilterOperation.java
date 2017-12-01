/**
 * 
 */
package org.vaadin.crudui.crud;

/**
 * @author Dmitrij Colautti
 *
 */
public enum FilterOperation implements Operation {
	EMPTY, APPLY;
	
	/* (non-Javadoc)
	 * @see org.vaadin.crudui.crud.Operation#getOperationName()
	 */
	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return name();
	}
}
