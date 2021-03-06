package org.vaadin.bread.core.ui.form;

import javax.persistence.metamodel.ManagedType;

import com.vaadin.data.HasValue;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * @author Dmitrij Colautti
 */
public interface FormFactory<T> {

    Component buildNewForm(OperationMode operationMode, T domainObject, boolean readOnly);

    void setOperationListener(OperationMode operationMode, OperationAction operationAction, Button.ClickListener operationButtonClickListener);
    
    void setVisibleProperties(OperationMode operationMode, String... properties);

    void setVisibleProperties(String... properties);

    void setDisabledProperties(OperationMode operationMode, String... properties);

    void setDisabledProperties(String... properties);

    void setFieldCaption(OperationMode operationMode, String property, String caption);

    void setFieldCaption(String property, String caption);

    void setFieldType(OperationMode operationMode, String property, Class<? extends HasValue> type);

    void setFieldType(String property, Class<? extends HasValue> type);

    void setFieldCreationListener(OperationMode operationMode, String property, FieldCreationListener listener);

    void setFieldCreationListener(String property, FieldCreationListener listener);

    void setFieldProvider(OperationMode operationMode, String property, FieldProvider provider);

    void setFieldProvider(String property, FieldProvider provider);

    void setValidationActive(OperationMode operationMode, boolean useBeanValidation);

    void setValidationActive(boolean useBeanValidation);

    void setErrorListener(ErrorListener<T> errorListener);

    void showError(OperationMode operationMode, OperationAction operationAction, T domainObject, Throwable e);

	void setJpaTypeForJpaValidation(OperationMode operationMode, ManagedType<?> managedType);

	void setJpaTypeForJpaValidation(ManagedType<?> managedType);
	
	FormConfiguration getConfiguration(OperationMode operationMode);

	void setButtonCaption(OperationAction operationAction, String caption);

	void setButtonCaption(OperationMode operationMode, OperationAction operationAction, String caption);

	void setButtonIcon(OperationMode operationMode, OperationAction operationAction, Resource resource);

	void setButtonIcon(OperationAction operationAction, Resource resource);

	void addButtonStyleName(OperationMode operationMode, OperationAction operationAction, String style);

	void addButtonStyleName(OperationAction operationAction, String style);

	ErrorListener<T> getErrorListener();

	OperationMode[] getOperationModes();

	void setValidationErrorMessage(String validationErrorMessage);

	String getValidationErrorMessage();

	void setValidationActive(OperationMode operationMode, OperationAction action, boolean useBeanValidation);

}
