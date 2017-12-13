package org.vaadin.bread.ui.form.impl.field.provider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import org.vaadin.bread.ui.form.FieldProvider;

import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.TextField;

/**
 * @author Alejandro Duarte.
 */
public class DefaultFieldProvider implements FieldProvider {

    private Class<?> type;

    public DefaultFieldProvider(Class<?> type) {
        this.type = type;
    }

    @Override
    public HasValue buildField() {
        if (Boolean.class.isAssignableFrom(type) || boolean.class == type) {
            return new CheckBox();
        }

        if (LocalDate.class.isAssignableFrom(type)) {
        	return new DateField();
        }

        if (LocalDateTime.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type)) {
        	DateTimeField dateField = new DateTimeField();
        	dateField.setResolution(DateTimeResolution.SECOND);
        	        	
            return dateField; 
        }

        if (Enum.class.isAssignableFrom(type)) {
            Object[] values = type.getEnumConstants();
            ComboBox comboBox = new ComboBox();
            comboBox.setItems(values);
            return comboBox;
        }

        if (Collection.class.isAssignableFrom(type)) {
            CheckBoxGroup comboBox = new CheckBoxGroup();
            return comboBox;
        }

        if (String.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type) || Byte.class.isAssignableFrom(type)
        		|| Number.class.isAssignableFrom(type) || type.isPrimitive()) {
            return new TextField();
        }

        ComboBox comboBox = new ComboBox();
        return comboBox;
    }

}
