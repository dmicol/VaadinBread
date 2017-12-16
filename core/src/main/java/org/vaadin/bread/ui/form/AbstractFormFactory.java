package org.vaadin.bread.ui.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.ManagedType;

import org.vaadin.bread.ui.crud.Operation;
import org.vaadin.bread.ui.crud.OperationAction;
import org.vaadin.bread.ui.crud.OperationMode;

import com.vaadin.data.HasValue;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

/**
 * @author Alejandro Duarte
 */
public abstract class AbstractFormFactory<T> implements FormFactory<T> {

    protected Map<Operation, BeanButtonFormConfiguration> configurations = new HashMap<>();

    protected ErrorListener<T> errorListener;
    protected OperationMode[] operationModes;
    
    /**
	 * 
	 */
	public AbstractFormFactory(OperationMode[] operationModes) {
		this.operationModes = operationModes;
	}
	
    public void setOperationListener(OperationMode operationMode, OperationAction action, Button.ClickListener operationButtonClickListener) {
        getConfiguration(operationMode).setOperationActionListener(action, operationButtonClickListener);
    }

    @Override
    public void setVisibleProperties(OperationMode operationMode, String... properties) {
        getConfiguration(operationMode).setVisibleProperties(new ArrayList<String>(Arrays.asList(properties)));
    }

    @Override
    public void setVisibleProperties(String... properties) {
        Arrays.stream(operationModes).forEach(operationMode -> setVisibleProperties(operationMode, properties));
    }

    @Override
    public void setDisabledProperties(OperationMode operationMode, String... properties) {
        getConfiguration(operationMode).setDisabledProperties(new HashSet<String>(Arrays.asList(properties)));
    }

    @Override
    public void setDisabledProperties(String... properties) {
        Arrays.stream(operationModes).forEach(operationMode -> setDisabledProperties(operationMode, properties));
    }

    @Override
    public void setFieldCaption(OperationMode operationMode, String property, String caption) {
        getConfiguration(operationMode).getFieldCaptions().put(property, caption);
    }

    @Override
    public void setFieldCaption(String property, String caption) {
        Arrays.stream(operationModes).forEach(operationMode -> setFieldCaption(operationMode, property, caption));
    }

    @Override
    public void setFieldType(OperationMode operationMode, String property, Class<? extends HasValue> type) {
        getConfiguration(operationMode).getFieldTypes().put(property, type);
    }

    @Override
    public void setFieldType(String property, Class<? extends HasValue> type) {
        Arrays.stream(operationModes).forEach(operationMode -> setFieldType(operationMode, property, type));
    }

    @Override
    public void setFieldCreationListener(OperationMode operationMode, String property, FieldCreationListener listener) {
        getConfiguration(operationMode).getFieldCreationListeners().put(property, listener);
    }

    @Override
    public void setFieldCreationListener(String property, FieldCreationListener listener) {
        Arrays.stream(operationModes).forEach(operationMode -> setFieldCreationListener(operationMode, property, listener));
    }

    @Override
    public void setFieldProvider(OperationMode operationMode, String property, FieldProvider provider) {
        getConfiguration(operationMode).getFieldProviders().put(property, provider);
    }

    @Override
    public void setFieldProvider(String property, FieldProvider provider) {
        Arrays.stream(operationModes).forEach(operationMode -> setFieldProvider(operationMode, property, provider));
    }

    @Override
    public void setButtonCaption(OperationMode operationMode, OperationAction operationAction, String caption) {
    	getConfiguration(operationMode).setButtonCaption(operationAction, caption);
    }

    @Override
    public void setButtonCaption(OperationAction operationAction, String caption) {
    	Arrays.stream(operationModes).forEach(operationMode -> setButtonCaption(operationMode, operationAction, caption));
    }

    @Override
    public void setButtonIcon(OperationMode operationMode, OperationAction operationAction, Resource resource) {
    	getConfiguration(operationMode).setButtonIcon(operationAction, resource);
    }

    @Override
    public void setButtonIcon(OperationAction operationAction, Resource resource) {
    	Arrays.stream(operationModes).forEach(operationMode -> setButtonIcon(operationMode, operationAction, resource));
    }

    @Override
    public void addButtonStyleName(OperationMode operationMode, OperationAction operationAction, String style) {
    	getConfiguration(operationMode).addButtonStyleName(operationAction, style);
    }

    @Override
    public void addButtonStyleName(OperationAction operationAction, String style) {
    	Arrays.stream(operationModes).forEach(operationMode -> addButtonStyleName(operationMode, operationAction, style));
    }
    
    protected Map<OperationAction, Set<String>> buttonStyleNames = new HashMap<>();
    
    
    
    
    

    @Override
    public void setUseBeanValidation(OperationMode operationMode, boolean useBeanValidation) {
        getConfiguration(operationMode).setUseBeanValidation(useBeanValidation);
    }

    @Override
    public void setUseBeanValidation(boolean useBeanValidation) {
        Arrays.stream(operationModes).forEach(operationMode -> setUseBeanValidation(operationMode, useBeanValidation));
    }

    @Override
    public void setJpaTypeForJpaValidation(OperationMode operationMode, ManagedType<?> managedType) {
        getConfiguration(operationMode).setJpaTypeForJpaValidation(managedType);
    }

    @Override
    public void setJpaTypeForJpaValidation(ManagedType<?> managedType) {
        Arrays.stream(operationModes).forEach(operationMode -> setJpaTypeForJpaValidation(operationMode, managedType));
    }

    @Override
    public void setErrorListener(ErrorListener<T> errorListener) {
        this.errorListener = errorListener;
    }
    
	public void buildSensitiveDefaults(Class<?> clazz) {
        Arrays.stream(operationModes).forEach(operationMode -> getConfiguration(operationMode).buildSensitiveDefaults(clazz));
        setErrorListener((opm, opa, obj, e) -> {e.printStackTrace(); Notification.show("ERROR: " + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);});

	}

    public BeanButtonFormConfiguration getConfiguration(OperationMode operationMode) {
        configurations.putIfAbsent(operationMode, new BeanButtonFormConfiguration());
        return configurations.get(operationMode);
    }

	@Override
	public OperationMode[] getOperationModes() {
		return operationModes;
	}

	public void setOperationModes(OperationMode[] operationModes) {
		this.operationModes = operationModes;
	}

	@Override
	public ErrorListener<T> getErrorListener() {
		return errorListener;
	}

}
