package org.vaadin.bread.ui.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.bread.ui.crud.OperationAction;
import org.vaadin.bread.ui.crud.Operation;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Button;

/**
 * @author Alejandro Duarte.
 */
@SuppressWarnings("serial")
public class FormConfiguration implements Serializable {

    protected List<String> visibleProperties = new ArrayList<>();
    protected List<String> disabledProperties = new ArrayList<>();
    protected List<String> fieldCaptions = new ArrayList<>();
    @SuppressWarnings("rawtypes")
	protected Map<Object, Class<? extends HasValue>> fieldTypes = new HashMap<>();
    protected Map<Object, FieldCreationListener> fieldCreationListeners = new HashMap<>();
    protected Map<Object, FieldProvider> fieldProviders = new HashMap<>();
    protected Map<OperationAction, Button.ClickListener> actionListeners = new HashMap<>();
    protected List<OperationAction> operationActions = new ArrayList<>();
    
    public void setOperationActionListener(OperationAction operation, Button.ClickListener operationButtonClickListener) {
    	actionListeners.put(operation, operationButtonClickListener);
    }
    
    public Button.ClickListener getOperationActionListener(OperationAction operation) {
    	return actionListeners.get(operation);
    }
    
    public List<String> getVisibleProperties() {
        return visibleProperties;
    }

    public void setVisibleProperties(List<String> visibleProperties) {
        this.visibleProperties = visibleProperties;
    }

    public List<String> getDisabledProperties() {
        return disabledProperties;
    }

    public void setDisabledProperties(List<String> disabledProperties) {
        this.disabledProperties = disabledProperties;
    }

    public List<String> getFieldCaptions() {
        return fieldCaptions;
    }

    public void setFieldCaptions(List<String> fieldCaptions) {
        this.fieldCaptions = fieldCaptions;
    }

    public Map<Object, Class<? extends HasValue>> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(Map<Object, Class<? extends HasValue>> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    public Map<Object, FieldCreationListener> getFieldCreationListeners() {
        return fieldCreationListeners;
    }

    public void setFieldCreationListeners(Map<Object, FieldCreationListener> fieldCreationListeners) {
        this.fieldCreationListeners = fieldCreationListeners;
    }

    public Map<Object, FieldProvider> getFieldProviders() {
        return fieldProviders;
    }

    public void setFieldProviders(Map<Object, FieldProvider> fieldProviders) {
        this.fieldProviders = fieldProviders;
    }

	public List<OperationAction> getOperationActions() {
		return operationActions;
	}

	public void setOperationActions(List<OperationAction> operations) {
		this.operationActions = operations;
	}

}
