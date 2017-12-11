/**
 * 
 */
package org.vaadin.bread.ui.form;

import org.vaadin.bread.ui.crud.OperationAction;
import org.vaadin.bread.ui.crud.OperationMode;

/**
 * @author Dmitrij Colautti
 *
 */
public interface ErrorListener<T> {

	void onError(OperationMode operationMode, OperationAction operationAction, T domainObject, Exception e);
}
