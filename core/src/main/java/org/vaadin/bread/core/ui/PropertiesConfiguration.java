package org.vaadin.bread.core.ui;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vaadin.data.util.BeanUtil;
import com.vaadin.shared.util.SharedUtil;

/**
 * @author Dmitrij Colautti.
 */
@SuppressWarnings("serial")
public class PropertiesConfiguration implements Serializable {

    protected List<String> visibleProperties = new ArrayList<>();
    protected List<String> propertiesOrder = new ArrayList<>();
    protected Set<String> disabledProperties = new HashSet<>();
    protected Map<String, String> propertiesCaption = new HashMap<>();
    
	public PropertiesConfiguration() {
	}
    
    public PropertiesConfiguration(Class<?> clazz) {
    	buildSensitiveDefaults(clazz);
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
    				propertiesCaption.put(pd.getName(), SharedUtil.propertyIdToHumanFriendly(pd.getName()));    		
    			}
    		});
    		
    	} catch (IntrospectionException e) {
    		throw new RuntimeException(e);
    	}
    	
    	
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

    public Map<String, String> getPropertiesCaption() {
        return propertiesCaption;
    }

    public void setPropertiesCaption(Map<String, String> fieldCaptions) {
        this.propertiesCaption = fieldCaptions;
    }

	public List<String> getPropertiesOrder() {
		return propertiesOrder;
	}

	public void setPropertiesOrder(List<String> propertiesOrder) {
		this.propertiesOrder = propertiesOrder;
	}
	


	public List<String> getVisibleOrderedProperties() {
		if (propertiesOrder==null || propertiesOrder.isEmpty())
			return new ArrayList<>(visibleProperties);
		
		LinkedHashSet<String> lls = new LinkedHashSet<>();
		lls.addAll(propertiesOrder);
		lls.addAll(visibleProperties);
		
		return new ArrayList<>(lls);
	}
}
