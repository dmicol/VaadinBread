/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;

import org.vaadin.bread.core.ui.form.impl.field.provider.DefaultFieldProvider;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.InMemoryDataProviderHelpers;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.util.BeanUtil;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * @author Dmitrij.colautti
 *
 */
public class FilterFieldComponent extends CssLayout {

	private Class<?> clazz;
	private boolean built = false;
	private ComboBox<PropertyDescriptor> field;
	private ComboBox<Operation> operation;
	private Component value1;
	private Component value2;
	private Button remove;
	private ClickListener removeListener;
	private Registration addClickListener;
	
	public FilterFieldComponent(Class<?> clazz) {
		this.clazz = clazz;
		
	}
	
	@Override
	public void attach() {
		super.attach();
		
		if (!built) {
			field = new ComboBox<>();

    		List<PropertyDescriptor> descriptors;
			try {
				descriptors = BeanUtil.getBeanPropertyDescriptors(clazz);
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}
			field.setDataProvider(new ListDataProvider<>(descriptors));
			field.setItemCaptionGenerator(pd-> pd.getName());
			field.setEmptySelectionAllowed(false);
			field.addSelectionListener(this::fieldSelected);
			addComponent(field);
			
			operation = new ComboBox<>();
			operation.setItemCaptionGenerator(pd-> pd.toString());
			operation.setEmptySelectionAllowed(false);
			operation.addSelectionListener(this::operationSelected);
			addComponent(field);
			
			remove = new Button();
			remove.setIcon(VaadinIcons.CLOSE);
			if (removeListener!=null)
				remove.addClickListener(removeListener);
		}
		built = true;
	}	

    public void fieldSelected(SingleSelectionEvent<PropertyDescriptor> event) {
    	OperationListFactory olf = new OperationListFactory();
    	operation.setDataProvider(new ListDataProvider<>(olf.retrive(event.getValue().getPropertyType())));
    	operation.clear();
    	addComponent(operation);
    }
    public void operationSelected(SingleSelectionEvent<Operation> event) {
    	if (value1!=null)
    		removeComponent(value1);		
    	if (value2!=null)
    		removeComponent(value2);
    	value1 = null;
    	value2 = null;
    	
    	Class<?> propertyType = field.getValue().getPropertyType();
    	
    	switch (event.getValue()) {
		case EQUALS:
		case NOT_EQUALS:
		case GRATER_THEN:
		case GRATER_EQUAL:
		case LESS_THEN:
		case LESS_EQUAL:
		case CONTAINS:
		case NOT_CONTAINS:
		case START_WITH:
		case END_WITH:
		case IN:
		case NOT_IN:
			value1 = (Component) DefaultFieldProvider.buildField(propertyType);
			addComponent(value1, 2);
			break;
	
		case EMPTY:
		case NOT_EMPTY:
			break;
			
		case RANGE:
			value1 = (Component) DefaultFieldProvider.buildField(propertyType);
			value2 = (Component) DefaultFieldProvider.buildField(propertyType);
			addComponent(value1, 2);
			addComponent(value2, 3);
			break;

		default:
			break;
		}
    }

	public ClickListener getRemoveListener() {
		return removeListener;
	}

	public void setRemoveListener(ClickListener removeListener) {
		if (addClickListener!=null) {
			addClickListener.remove();
		}
		this.removeListener = removeListener;
		addClickListener = remove.addClickListener(removeListener);
	}
	
	public FilterValue getValue() {
		if (operation.getValue()==null || field.getValue()==null)
			return null;
		
		FilterValue value = new FilterValue();
		
		value.setOperation(operation.getValue());
		value.setProperty(field.getValue());
		if (value1!=null)
			value.setValue1(((HasValue)value1).getValue());
		if (value2!=null)
			value.setValue2(((HasValue)value2).getValue());
		
		return value;
	}
}
