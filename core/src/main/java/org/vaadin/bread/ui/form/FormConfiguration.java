package org.vaadin.bread.ui.form;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;

import org.vaadin.bread.ui.crud.OperationAction;
import org.vaadin.bread.ui.form.impl.field.provider.DefaultFieldProvider;

import com.vaadin.data.HasValue;
import com.vaadin.data.util.BeanUtil;
import com.vaadin.shared.util.SharedUtil;
import com.vaadin.ui.Button;

/**
 * @author Alejandro Duarte.
 */
@SuppressWarnings("serial")
public class FormConfiguration implements Serializable {

    protected List<String> visibleProperties = new ArrayList<>();
    protected Set<String> disabledProperties = new HashSet<>();
    protected Map<String, String> fieldCaptions = new HashMap<>();
    @SuppressWarnings("rawtypes")
	protected Map<Object, Class<? extends HasValue>> fieldTypes = new HashMap<>();
    protected Map<Object, FieldCreationListener> fieldCreationListeners = new HashMap<>();
    protected Map<Object, FieldProvider> fieldProviders = new HashMap<>();
    protected Map<OperationAction, Button.ClickListener> actionListeners = new HashMap<>();
    protected List<OperationAction> operationActions = new ArrayList<>();
    
	protected boolean useBeanValidation = true;
	protected ManagedType<?> jpaTypeForJpaValidation;
    
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

    public Set<String> getDisabledProperties() {
        return disabledProperties;
    }

    public void setDisabledProperties(Set<String> disabledProperties) {
        this.disabledProperties = disabledProperties;
    }

    public Map<String, String> getFieldCaptions() {
        return fieldCaptions;
    }

    public void setFieldCaptions(Map<String, String> fieldCaptions) {
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

	public boolean isUseBeanValidation() {
	    return useBeanValidation;
	}

	public void setUseBeanValidation(boolean useBeanValidation) {
	    this.useBeanValidation = useBeanValidation;
	}

	public ManagedType<?> getJpaTypeForJpaValidation() {
		return jpaTypeForJpaValidation;
	}

	public void setJpaTypeForJpaValidation(ManagedType<?> jpaTypeForJpaValidation) {
		this.jpaTypeForJpaValidation = jpaTypeForJpaValidation;
	}


	public void buildSensitiveDefaults(Class<?> clazz) {
	    try {
	        List<PropertyDescriptor> descriptors = BeanUtil.getBeanPropertyDescriptors(clazz);
	        descriptors.stream()
	        	.filter(d -> !d.getName().equals("class"))
	        	.forEach(pd -> {
	        		Class<?> propertyType = pd.getPropertyType();
	        		if (!(propertyType.isArray() || Collection.class.isAssignableFrom(propertyType))) {
	        			visibleProperties.add(pd.getName());
	        			fieldCaptions.put(pd.getName(), SharedUtil.propertyIdToHumanFriendly(pd.getName()));
	        			
	        			if (jpaTypeForJpaValidation!=null) {
	        				Attribute<?, ?> attribute = jpaTypeForJpaValidation.getAttribute(pd.getName());
	        				if (attribute.getJavaMember() instanceof AnnotatedElement) {
	        					AnnotatedElement annotated = (AnnotatedElement)attribute.getJavaMember();
	        					if (annotated!=null && annotated.getAnnotation(GeneratedValue.class)!=null) {
	        						disabledProperties.add(pd.getName());
	        					}
	        				}
	        			}
	        			fieldProviders.put(pd.getName(), new DefaultFieldProvider(pd.getPropertyType()));            		
	        		}
	        	});
	        
	    } catch (IntrospectionException e) {
	        throw new RuntimeException(e);
	    }
	}

}
