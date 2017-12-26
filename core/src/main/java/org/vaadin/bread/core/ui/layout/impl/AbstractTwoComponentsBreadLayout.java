package org.vaadin.bread.core.ui.layout.impl;

import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.layout.BreadLayout;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Alejandro Duarte.
 */
public abstract class AbstractTwoComponentsBreadLayout extends AbstractBreadLayout implements BreadLayout {

    protected VerticalLayout detailComponent = new VerticalLayout();
    protected HorizontalLayout detailComponentHeaderLayout = new HorizontalLayout();
    protected VerticalLayout formComponentLayout = new VerticalLayout();
    protected VerticalLayout formCaptionLayout = new VerticalLayout();

    
    public AbstractTwoComponentsBreadLayout() {
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
