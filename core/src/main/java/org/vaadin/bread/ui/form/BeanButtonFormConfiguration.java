/**
 * 
 */
package org.vaadin.bread.ui.form;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vaadin.bread.ui.crud.OperationAction;

import com.vaadin.data.util.BeanUtil;
import com.vaadin.server.Resource;
import com.vaadin.shared.util.SharedUtil;

/**
 * @author Dmitrij Colautti
 *
 */
@SuppressWarnings("serial")
public class BeanButtonFormConfiguration extends FormConfiguration {

    protected Map<OperationAction, String> buttonCaptions = new HashMap<>();
    protected Map<OperationAction, Resource> buttonIcons = new HashMap<>();
    protected Map<OperationAction, Set<String>> buttonStyleNames = new HashMap<>();
    
    /* (non-Javadoc)
     * @see org.vaadin.bread.ui.form.BeanFormConfiguration#buildSensitiveDefaults(java.lang.Class)
     */
    @Override
    public void buildSensitiveDefaults(Class<?> clazz) {
    	super.buildSensitiveDefaults(clazz);

    	operationActions.stream().forEach(op->
    		setButtonCaption(op, SharedUtil.propertyIdToHumanFriendly(op.getOperationName()))
		);

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
}
