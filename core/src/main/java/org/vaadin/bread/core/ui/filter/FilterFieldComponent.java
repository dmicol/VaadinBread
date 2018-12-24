/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;

import org.vaadin.bread.core.ui.form.impl.field.provider.DefaultFieldProvider;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.util.BeanUtil;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * @author Dmitrij.colautti
 *
 */
public class FilterFieldComponent extends CssLayout {
	private static final long serialVersionUID = 1L;
	
	private Class<?> clazz;
	private boolean built = false;
	private ComboBox<PropertyDescriptor> field;
	private ComboBox<Operation> operation;
	private Component value1;
	private Component value2;
	private Button add;
	private Button remove;
	private ClickListener removeListener;
	private ClickListener addListener;
	private Registration addClickListener;
	private Registration removeClickListener;
	
	public FilterFieldComponent(Class<?> clazz) {
		this.clazz = clazz;
//		setWidth(100, Unit.PERCENTAGE);
		setWidthUndefined();
		addStyleName("FilterFieldComponent");
	}
	
	@Override
	public void attach() {
		super.attach();
		
		if (!built) {
			
			add = new Button();
			add.setIcon(VaadinIcons.PLUS);
			addComponent(add);
			
			remove = new Button();
			remove.setIcon(VaadinIcons.CLOSE);
			addComponent(remove);

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
		}
		built = true;
	}	

    public void fieldSelected(SingleSelectionEvent<PropertyDescriptor> event) {
    	OperationListFactory olf = new OperationListFactory();
    	operation.setDataProvider(new ListDataProvider<>(olf.retrive(event.getValue().getPropertyType())));
    	operation.clear();
    	addComponent(operation, 3);
    }
    public void operationSelected(SingleSelectionEvent<Operation> event) {
    	if (value1!=null)
    		removeComponent(value1);		
    	if (value2!=null)
    		removeComponent(value2);
    	value1 = null;
    	value2 = null;
    	
    	if (event.getValue()==null)
    		return;
    	
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
			addComponent(value1, 4);
			break;
	
		case EMPTY:
		case NOT_EMPTY:
			break;
			
		case RANGE:
			value1 = (Component) DefaultFieldProvider.buildField(propertyType);
			value2 = (Component) DefaultFieldProvider.buildField(propertyType);
			addComponent(value1, 4);
			addComponent(value2, 5);
			break;

		default:
			break;
		}
    }

	public ClickListener getRemoveListener() {
		return removeListener;
	}

	public void setRemoveListener(ClickListener removeListener) {
		if (removeClickListener!=null) {
			removeClickListener.remove();
			removeComponent(remove);
		}
		this.removeListener = removeListener;
		removeClickListener = remove.addClickListener(removeListener);
	}

	public void setAddListener(ClickListener addListener) {
		if (addClickListener!=null) {
			addClickListener.remove();
			removeComponent(add);
		}
		this.addListener = addListener;
		addClickListener = add.addClickListener(addListener);
	}
	
	@SuppressWarnings("rawtypes")
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
