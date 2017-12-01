package org.vaadin.crudui.form;

import java.util.function.Consumer;

import javax.persistence.metamodel.ManagedType;

import org.vaadin.crudui.crud.Operation;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * @author Dmitrij Colautti
 */
public interface FormFactory<T> {

    Component buildNewForm(Operation operation, T domainObject, boolean readOnly);

    void setOperationListener(Operation operation, Button.ClickListener operationButtonClickListener);
    
    void setVisibleProperties(Operation operation, String... properties);

    void setVisibleProperties(String... properties);

    void setDisabledProperties(Operation operation, String... properties);

    void setDisabledProperties(String... properties);

    void setFieldCaptions(Operation operation, String... captions);

    void setFieldCaptions(String... captions);

    void setFieldType(Operation operation, String property, Class<? extends HasValue> type);

    void setFieldType(String property, Class<? extends HasValue> type);

    void setFieldCreationListener(Operation operation, String property, FieldCreationListener listener);

    void setFieldCreationListener(String property, FieldCreationListener listener);

    void setFieldProvider(Operation operation, String property, FieldProvider provider);

    void setFieldProvider(String property, FieldProvider provider);

    void setUseBeanValidation(Operation operation, boolean useBeanValidation);

    void setUseBeanValidation(boolean useBeanValidation);

    void setErrorListener(Consumer<Exception> errorListener);

    void showError(Operation operation, Exception e);

	void setJpaTypeForJpaValidation(Operation operation, ManagedType<?> managedType);

	void setJpaTypeForJpaValidation(ManagedType<?> managedType);
	
	FormConfiguration getConfiguration(Operation operation);

}
