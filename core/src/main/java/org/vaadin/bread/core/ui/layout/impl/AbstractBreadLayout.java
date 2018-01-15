/**
 * 
 */
package org.vaadin.bread.core.ui.layout.impl;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.layout.BreadLayout;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
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
	
	private static final long serialVersionUID = -6169042588367185056L;

	protected VerticalLayout breadComponent = new VerticalLayout();
	protected HorizontalLayout browseComponentHeaderLayout = new HorizontalLayout();
	protected CssLayout toolbarLayout = new CssLayout();
	protected TabSheet filterTabs = new TabSheet();
	protected VerticalLayout filterLayout = new VerticalLayout();
	protected VerticalLayout browseComponentLayout = new VerticalLayout();
	

    protected VerticalLayout detailComponent = new VerticalLayout();
    protected HorizontalLayout detailComponentHeaderLayout = new HorizontalLayout();
    
    protected VerticalLayout formComponentLayout = new VerticalLayout();
    protected VerticalLayout formCaptionLayout = new VerticalLayout();
	
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
        filterTabs.setTabsVisible(false);
        
        filterLayout.setWidth(100, Unit.PERCENTAGE);
        filterLayout.setMargin(false);
        filterLayout.addComponent(filterTabs);
        
        browseComponentHeaderLayout.addComponent(filterLayout);
        browseComponentHeaderLayout.setExpandRatio(filterLayout, 1);
        breadComponent.addComponent(browseComponentHeaderLayout);

        browseComponentLayout.setSizeFull();
        browseComponentLayout.setMargin(false);
        breadComponent.addComponent(browseComponentLayout);
        breadComponent.setExpandRatio(browseComponentLayout, 1);
                

        detailComponent.setWidth("100%");
        detailComponent.setMargin(false);
        detailComponent.setSpacing(true);

        detailComponentHeaderLayout.setVisible(false);
        detailComponentHeaderLayout.setSpacing(true);

        formCaptionLayout.setMargin(new MarginInfo(false, true, false, true));

        formComponentLayout.setSizeFull();
        formComponentLayout.setMargin(false);
        detailComponent.addComponent(formComponentLayout);
        detailComponent.setExpandRatio(formComponentLayout, 1);
        
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
	    
	    if (filterTabs.getComponentCount()>1)
	    	filterTabs.setTabsVisible(true);
	}

	@Override
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
	
	@Override
	public void showForm(CrudOperation operation, Component form) {
	    String caption = detailCaptions.get(operation);
	    if (caption != null) {
	        Label label = new Label(caption);
	        label.addStyleName(ValoTheme.LABEL_COLORED);
	        formCaptionLayout.removeAllComponents();
	        formCaptionLayout.addComponent(label);
	        detailComponent.addComponent(formCaptionLayout, detailComponent.getComponentCount() - 1);
	    } else {
	        detailComponent.removeComponent(formCaptionLayout);
	    }
	
	    formComponentLayout.removeAllComponents();
	    formComponentLayout.addComponent(form);
	}

	@Override
	public void hideForm() {
	    formComponentLayout.removeAllComponents();
	    detailComponent.removeComponent(formCaptionLayout);
	}

}