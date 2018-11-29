/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Dmitrij.colautti
 *
 */
public class FilterComponent extends VerticalLayout {

	private Class<?> clazz;
	private ArrayList<FilterFieldComponent> filters = new ArrayList<>(); 
	boolean built=false;
	
	public FilterComponent(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void attach() {
		super.attach();
		if (!built) {
			addFilter();
			built=true;
		}
	}
	
	private void removeListener(ClickEvent e) {
		HasComponents parent = e.getButton().getParent();
		
		filters.remove(parent);
		removeComponent(parent);
		
		if (filters.isEmpty()) {
			addFilter();
		}
	}

	private void addFilter() {		
		FilterFieldComponent filterFieldComponent = new FilterFieldComponent(clazz);
		addComponent(filterFieldComponent);
		filters.add(filterFieldComponent);
		filterFieldComponent.setRemoveListener(this::removeListener);
	}
	
	public List<FilterValue> getValue() {
		return filters.stream()
				.map(FilterFieldComponent::getValue)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
