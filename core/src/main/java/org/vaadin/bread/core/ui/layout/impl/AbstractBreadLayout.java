/**
 * 
 */
package org.vaadin.bread.core.ui.layout.impl;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.form.FilterOperation;
import org.vaadin.bread.core.ui.form.FormConfiguration;
import org.vaadin.bread.core.ui.layout.BreadLayout;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Dmitrij Colautti
 *
 */
public abstract class AbstractBreadLayout extends Composite implements BreadLayout{

	protected VerticalLayout breadComponent = new VerticalLayout();
	protected HorizontalLayout browseComponentHeaderLayout = new HorizontalLayout();
	protected CssLayout toolbarLayout = new CssLayout();
	protected TabSheet filterTabs = new TabSheet();
	protected VerticalLayout browseComponentLayout = new VerticalLayout();
	protected Label captionLabel = new Label();
    protected Map<CrudOperation, String> detailCaptions = new HashMap<>();

	/**
	 * 
	 */
	public AbstractBreadLayout() {
		super();
        breadComponent.setSizeFull();
        breadComponent.setMargin(false);
        breadComponent.setSpacing(true);
        breadComponent.addComponent(captionLabel);

        captionLabel.addStyleName(ValoTheme.LABEL_COLORED);
        captionLabel.addStyleName(ValoTheme.LABEL_BOLD);
        captionLabel.addStyleName(ValoTheme.LABEL_H3);
        captionLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        captionLabel.setVisible(false);

        browseComponentHeaderLayout.setWidthUndefined();
        browseComponentHeaderLayout.setWidth(100, Unit.PERCENTAGE);
        browseComponentHeaderLayout.setVisible(false);
        browseComponentHeaderLayout.setSpacing(true);

        toolbarLayout.setVisible(false);
        toolbarLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        Button filterIcon = new Button(VaadinIcons.SEARCH);
        filterIcon.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        filterIcon.addClickListener(e -> filterTabs.setVisible(!filterTabs.isVisible()) );
        browseComponentHeaderLayout.addComponent(filterIcon);

        filterTabs.setVisible(false);
        filterTabs.setHeightUndefined();;
        filterTabs.setWidth(100, Unit.PERCENTAGE);
        filterTabs.setTabCaptionsAsHtml(true);
        browseComponentHeaderLayout.addComponent(filterTabs);
        browseComponentHeaderLayout.setExpandRatio(filterTabs, 1);
        breadComponent.addComponent(browseComponentHeaderLayout);

        browseComponentLayout.setSizeFull();
        browseComponentLayout.setMargin(false);
        breadComponent.addComponent(browseComponentLayout);
        breadComponent.setExpandRatio(browseComponentLayout, 1);
        
        setDetailCaption(CrudOperation.DELETE, "Are you sure you want to delete this item?");
	}

	/**
	 * @param compositionRoot
	 */
	public AbstractBreadLayout(AbstractComponent compositionRoot) {
		super(compositionRoot);
	}

	protected abstract void addToolbarLayout(CssLayout toolbarLayout);

	@Override
	public void setCaption(String caption) {
	    if (!captionLabel.isVisible()) {
	        captionLabel.setVisible(true);
	    }
	
	    captionLabel.setValue(caption);
	}

	@Override
	public void setMainComponent(Component component) {
	    browseComponentLayout.removeAllComponents();
	    browseComponentLayout.addComponent(component);
	}

	@Override
	public void addFilterComponent(String filterCaption, Component component) {
	    if (!browseComponentHeaderLayout.isVisible()) {
	        browseComponentHeaderLayout.setVisible(true);
	    }
	
	    filterTabs.setVisible(true);
	    filterTabs.addTab(component, filterCaption);
	}

	public void setDetailCaption(CrudOperation operation, String caption) {
	    detailCaptions.put(operation, caption);
	}
	
	@Override
	public void setFiltersVisible(boolean visible) {
		filterTabs.setVisible(visible);
	}
	
	@Override
	public boolean isFiltersVisible() {
		return filterTabs.isVisible();
	}

}