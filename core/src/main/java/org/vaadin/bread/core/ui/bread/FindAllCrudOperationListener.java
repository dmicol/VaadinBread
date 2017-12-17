package org.vaadin.bread.core.ui.bread;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Alejandro Duarte.
 */
@FunctionalInterface
public interface FindAllCrudOperationListener<T> extends Serializable {

    Collection<T> findAll();

}
