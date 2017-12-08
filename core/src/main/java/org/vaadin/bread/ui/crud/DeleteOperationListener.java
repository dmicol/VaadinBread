package org.vaadin.bread.ui.crud;

import java.io.Serializable;

/**
 * @author Alejandro Duarte.
 */
@FunctionalInterface
public interface DeleteOperationListener<T> extends Serializable {

    void perform(T domainObject);

}
