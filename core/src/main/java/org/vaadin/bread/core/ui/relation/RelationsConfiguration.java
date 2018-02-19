package org.vaadin.bread.core.ui.relation;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.bread.core.ui.PropertiesConfiguration;

import com.vaadin.data.util.BeanUtil;
import com.vaadin.shared.util.SharedUtil;

/**
 * @author Dmitrij Colautti.
 */
@SuppressWarnings("serial")
public class RelationsConfiguration implements Serializable {

	protected Map<String, PropertiesConfiguration> relationsConfiguration = new HashMap<>();

    protected PropertiesConfiguration configuration = new PropertiesConfiguration();

	public RelationsConfiguration() {
	}

	public RelationsConfiguration(Class<?> clazz) {
		buildSensitiveDefaults(clazz);
	}
	
	public void buildSensitiveDefaults(Class<?> clazz) {
		try {
			BeanUtil.getBeanPropertyDescriptors(clazz).stream()
				.filter(pd -> !pd.getPropertyType().isPrimitive())
				.forEach(property -> {
					configuration.getVisibleProperties().add(property.getName());
					configuration.getPropertiesCaption().put(property.getName(), SharedUtil.propertyIdToHumanFriendly(property.getName()));
					relationsConfiguration.putIfAbsent(property.getName(), new PropertiesConfiguration())
						.buildSensitiveDefaults(property.getPropertyType());
				});
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}


    }
}
