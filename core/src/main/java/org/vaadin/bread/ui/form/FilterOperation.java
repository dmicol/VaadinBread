/**
 * 
 */
package org.vaadin.bread.ui.form;

/**
 * @author Dmitrij Colautti
 *
 */
public enum FilterOperation implements OperationMode, OperationAction {
	EMPTY, APPLY;

	/* (non-Javadoc)
	 * @see org.vaadin.bread.ui.crud.OperationAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return name();
	}

	/* (non-Javadoc)
	 * @see org.vaadin.bread.ui.crud.OperationMode#getOperationModeName()
	 */
	@Override
	public String getOperationModeName() {
		return name();
	}
}
