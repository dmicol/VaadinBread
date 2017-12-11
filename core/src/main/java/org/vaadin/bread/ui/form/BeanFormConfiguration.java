/**
 * 
 */
package org.vaadin.bread.ui.form;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;

import org.vaadin.bread.ui.form.impl.field.provider.DefaultFieldProvider;

import com.vaadin.data.util.BeanUtil;
import com.vaadin.shared.util.SharedUtil;

/**
 * @author Dmitrij Colautti
 *
 */
@SuppressWarnings("serial")
public class BeanFormConfiguration extends FormConfiguration {

	protected boolean useBeanValidation = true;
	protected ManagedType<?> jpaTypeForJpaValidation;

	public void buildSensitiveDefaults(Class<?> clazz) {
	    try {
	        List<PropertyDescriptor> descriptors = BeanUtil.getBeanPropertyDescriptors(clazz);
	        descriptors.stream()
	        	.filter(d -> !d.getName().equals("class"))
	        	.forEach(pd -> {
	        		Class<?> propertyType = pd.getPropertyType();
	        		if (!(propertyType.isArray() || Collection.class.isAssignableFrom(propertyType))) {
	        			visibleProperties.add(pd.getName());
	        			fieldCaptions.add(SharedUtil.propertyIdToHumanFriendly(pd.getName()));
	        			
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

}
