package org.vaadin.bread.core.ui.bread;

import java.io.Serializable;

import com.vaadin.data.provider.DataProvider;

/**
 * @author Alejandro Duarte
 */
public interface BreadListener<T> extends Serializable {

    DataProvider<T, ?> getDataProvider();

    T add(T domainObjectToAdd);

    T update(T domainObjectToUpdate);

    void delete(T domainObjectToDelete);

}
