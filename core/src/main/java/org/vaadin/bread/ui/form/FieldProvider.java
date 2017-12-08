package org.vaadin.bread.ui.form;

import java.io.Serializable;

import com.vaadin.data.HasValue;

/**
 * @author Alejandro Duarte.
 */
@FunctionalInterface
public interface FieldProvider extends Serializable {

    HasValue buildField();

}
