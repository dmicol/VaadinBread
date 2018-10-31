package org.vaadin.bread.core.ui.form;

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
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;

import org.vaadin.bread.core.ui.PropertiesConfiguration;
import org.vaadin.bread.core.ui.form.impl.field.provider.DefaultFieldProvider;

import com.vaadin.data.HasValue;
import com.vaadin.data.util.BeanUtil;
import com.vaadin.server.Resource;
import com.vaadin.shared.util.SharedUtil;
import com.vaadin.ui.Button;

/**
 * @author Alejandro Duarte.
 */
@SuppressWarnings("serial")
public class FormConfiguration implements Serializable {

	protected PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
    @SuppressWarnings("rawtypes")
	protected Map<Object, Class<? extends HasValue>> fieldTypes = new HashMap<>();
    protected Map<Object, FieldCreationListener> fieldCreationListeners = new HashMap<>();
    protected Map<Object, FieldProvider> fieldProviders = new HashMap<>();
    protected Map<OperationAction, Button.ClickListener> actionListeners = new HashMap<>();
    protected List<OperationAction> operationActions = new ArrayList<>();
    
    protected Map<OperationAction, String> buttonCaptions = new HashMap<>();
    protected Map<OperationAction, Resource> buttonIcons = new HashMap<>();
    protected Map<OperationAction, Set<String>> buttonStyleNames = new HashMap<>();
    protected Map<OperationAction, Boolean> validationActive = new HashMap<>();
    
    protected boolean useBeanValidation = true;
	protected ManagedType<?> jpaTypeForJpaValidation;

	public ManagedType<?> getJpaTypeForJpaValidation() {
		return jpaTypeForJpaValidation;
	}

	public void setJpaTypeForJpaValidation(ManagedType<?> jpaTypeForJpaValidation) {
		this.jpaTypeForJpaValidation = jpaTypeForJpaValidation;
	}
	
    public void setOperationActionListener(OperationAction operation, Button.ClickListener operationButtonClickListener) {
    	actionListeners.put(operation, operationButtonClickListener);
    }
    
    public Button.ClickListener getOperationActionListener(OperationAction operation) {
    	return actionListeners.get(operation);
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
    
    public String getButtonCaption(OperationAction action) {
    	return buttonCaptions.get(action);
    }
    
    public void setButtonCaption(OperationAction action, String caption) {
        buttonCaptions.put(action, caption);
    }

    public Resource getButtonIcon(OperationAction action) {
        return buttonIcons.get(action);
    }

    public void setButtonIcon(OperationAction action, Resource icon) {
        buttonIcons.put(action, icon);
    }

    public Set<String> getButtonStyleNames(OperationAction action) {
        return buttonStyleNames.get(action);
    }

    public void addButtonStyleName(OperationAction action, String styleName) {
        buttonStyleNames.putIfAbsent(action, new HashSet<>());
        buttonStyleNames.get(action).add(styleName);
    }

	public Map<OperationAction, String> getButtonCaptions() {
		return buttonCaptions;
	}

	public void setButtonCaptions(Map<OperationAction, String> buttonCaptions) {
		this.buttonCaptions = buttonCaptions;
	}

	public Map<OperationAction, Resource> getButtonIcons() {
		return buttonIcons;
	}

	public void setButtonIcons(Map<OperationAction, Resource> buttonIcons) {
		this.buttonIcons = buttonIcons;
	}

	public Map<OperationAction, Set<String>> getButtonStyleNames() {
		return buttonStyleNames;
	}

	public void setButtonStyleNames(Map<OperationAction, Set<String>> buttonStyleNames) {
		this.buttonStyleNames = buttonStyleNames;
	}
	

	public void buildSensitiveDefaults(Class<?> clazz) {
		if (propertiesConfiguration.getVisibleProperties().isEmpty()) {
			propertiesConfiguration.buildSensitiveDefaults(clazz);
		    try {
		        List<PropertyDescriptor> descriptors = BeanUtil.getBeanPropertyDescriptors(clazz);
		        descriptors.stream()
		        	.filter(d -> !d.getName().equals("class"))
		        	.forEach(pd -> {
		        		Class<?> propertyType = pd.getPropertyType();
		        		if (!(propertyType.isArray() || Collection.class.isAssignableFrom(propertyType))) {
		        			fieldProviders.put(pd.getName(), new DefaultFieldProvider(pd.getPropertyType()));            		
		        		}
		        	});
		        
		    } catch (IntrospectionException e) {
		        throw new RuntimeException(e);
		    }
		}
	    

    	if (jpaTypeForJpaValidation!=null) {
    		propertiesConfiguration.getVisibleProperties().stream().forEach(prodId -> {
    				try {
	    				Attribute<?, ?> attribute = jpaTypeForJpaValidation.getAttribute(prodId);
	    				if (attribute.getJavaMember() instanceof AnnotatedElement) {
	    					AnnotatedElement annotated = (AnnotatedElement)attribute.getJavaMember();
	    					if (annotated!=null && annotated.getAnnotation(GeneratedValue.class)!=null) {
	    						propertiesConfiguration.getDisabledProperties()	.add(prodId);
	    					}
	    				}
					} catch (IllegalArgumentException e) { 
						// No OP 
					}
	        	});
    	}
    	
    	operationActions.stream().forEach(op->
    		setButtonCaption(op, SharedUtil.propertyIdToHumanFriendly(op.getActionName()))
		);

    }
    
    public boolean getValidationActive(OperationAction action) {
    	return validationActive.get(action)==Boolean.TRUE ? true : false;
    }
    
    public void setValidationActive(OperationAction action, boolean val) {
    	validationActive.put(action, val);
    }

	public Map<OperationAction, Boolean> getValidationActive() {
		return validationActive;
	}

	public void setValidationActive(Map<OperationAction, Boolean> useBeanValidation) {
		this.validationActive = useBeanValidation;
	}

	public boolean isValidationActive() {
		return validationActive.values().stream().collect(Collectors.reducing((v1,v2) -> v1 || v2)).orElse(false);
	}

	public boolean isUseBeanValidation() {
		return useBeanValidation;
	}

	public void setUseBeanValidation(boolean useBeanValidation) {
		this.useBeanValidation = useBeanValidation;
	}

	public PropertiesConfiguration getPropertiesConfiguration() {
		return propertiesConfiguration;
	}

	public void setPropertiesConfiguration(PropertiesConfiguration propertiesConfiguration) {
		this.propertiesConfiguration = propertiesConfiguration;
	}
}
