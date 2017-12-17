/**
 * 
 */
package org.vaadin.bread.ui.form;

/**
 * @author Dmitrij Colautti
 *
 */
public interface ErrorListener<T> {

	void onError(OperationMode operationMode, OperationAction operationAction, T domainObject, Exception e);
}
