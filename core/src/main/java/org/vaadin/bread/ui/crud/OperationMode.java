/**
 * 
 */
package org.vaadin.bread.ui.crud;

/**
 * @author Dmitrij Colautti
 *
 */
public interface OperationMode extends Operation {
	
	public default String getOperationModeName() {
		return getOperationName();
	}
}
