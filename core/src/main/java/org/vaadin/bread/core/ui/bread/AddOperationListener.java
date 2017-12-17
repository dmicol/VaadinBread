package org.vaadin.bread.core.ui.bread;

import java.io.Serializable;

/**
 * @author Alejandro Duarte.
 */
@FunctionalInterface
public interface AddOperationListener<T> extends Serializable {

    T perform(T domainObject);

}
