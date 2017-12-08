package org.vaadin.bread.ui.form;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.metamodel.ManagedType;

import org.vaadin.bread.ui.crud.Operation;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Button;

/**
 * @author Alejandro Duarte
 */
public abstract class AbstractFormFactory<T> implements FormFactory<T> {

    protected Map<Operation, FormConfiguration> configurations = new HashMap<>();

    protected ErrorListener<T> errorListener;
    protected Operation[] operations;
    
    /**
	 * 
	 */
	public AbstractFormFactory(Operation[] operations) {
		this.operations = operations;
	}
	
    public void setOperationListener(Operation operation, Button.ClickListener operationButtonClickListener) {
        getConfiguration(operation).setOperationListener(operation, operationButtonClickListener);
    }

    @Override
    public void setVisibleProperties(Operation operation, String... properties) {
        getConfiguration(operation).setVisibleProperties(Arrays.asList(properties));
    }

    @Override
    public void setVisibleProperties(String... properties) {
        Arrays.stream(operations).forEach(operation -> setVisibleProperties(operation, properties));
    }

    @Override
    public void setDisabledProperties(Operation operation, String... properties) {
        getConfiguration(operation).setDisabledProperties(Arrays.asList(properties));
    }

    @Override
    public void setDisabledProperties(String... properties) {
        Arrays.stream(operations).forEach(operation -> setDisabledProperties(operation, properties));
    }

    @Override
    public void setFieldCaptions(Operation operation, String... captions) {
        getConfiguration(operation).setFieldCaptions(Arrays.asList(captions));
    }

    @Override
    public void setFieldCaptions(String... captions) {
        Arrays.stream(operations).forEach(operation -> setFieldCaptions(operation, captions));
    }

    @Override
    public void setFieldType(Operation operation, String property, Class<? extends HasValue> type) {
        getConfiguration(operation).getFieldTypes().put(property, type);
    }

    @Override
    public void setFieldType(String property, Class<? extends HasValue> type) {
        Arrays.stream(operations).forEach(operation -> setFieldType(operation, property, type));
    }

    @Override
    public void setFieldCreationListener(Operation operation, String property, FieldCreationListener listener) {
        getConfiguration(operation).getFieldCreationListeners().put(property, listener);
    }

    @Override
    public void setFieldCreationListener(String property, FieldCreationListener listener) {
        Arrays.stream(operations).forEach(operation -> setFieldCreationListener(operation, property, listener));
    }

    @Override
    public void setFieldProvider(Operation operation, String property, FieldProvider provider) {
        getConfiguration(operation).getFieldProviders().put(property, provider);
    }

    @Override
    public void setFieldProvider(String property, FieldProvider provider) {
        Arrays.stream(operations).forEach(operation -> setFieldProvider(operation, property, provider));
    }

    @Override
    public void setUseBeanValidation(Operation operation, boolean useBeanValidation) {
        getConfiguration(operation).setUseBeanValidation(useBeanValidation);
    }

    @Override
    public void setUseBeanValidation(boolean useBeanValidation) {
        Arrays.stream(operations).forEach(operation -> setUseBeanValidation(operation, useBeanValidation));
    }

    @Override
    public void setJpaTypeForJpaValidation(Operation operation, ManagedType<?> managedType) {
        getConfiguration(operation).setJpaTypeForJpaValidation(managedType);
    }

    @Override
    public void setJpaTypeForJpaValidation(ManagedType<?> managedType) {
        Arrays.stream(operations).forEach(operation -> setJpaTypeForJpaValidation(operation, managedType));
    }

    @Override
    public void setErrorListener(ErrorListener<T> errorListener) {
        this.errorListener = errorListener;
    }

    public FormConfiguration getConfiguration(Operation operation) {
        configurations.putIfAbsent(operation, new FormConfiguration());
        return configurations.get(operation);
    }

}
