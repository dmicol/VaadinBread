package org.vaadin.bread.core.ui.bread;

import java.io.Serializable;

/**
 * @author Alejandro Duarte.
 */
@FunctionalInterface
public interface DeleteOperationListener<T> extends Serializable {

    void perform(T domainObject);

}
