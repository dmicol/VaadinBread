/**
 * 
 */
package org.vaadin.bread.ui.crud;

/**
 * @author Dmitrij Colautti
 *
 */
public interface OperationAction extends Operation {
	
	public default String getActionName() {
		return getOperationName();
	}
}
