/**
 * 
 */
package org.vaadin.bread.ui.form;

import org.vaadin.bread.ui.crud.Operation;

/**
 * @author Dmitrij Colautti
 *
 */
public interface ErrorListener<T> {

	void onError(Operation operation, T domainObject, Exception e);
}
