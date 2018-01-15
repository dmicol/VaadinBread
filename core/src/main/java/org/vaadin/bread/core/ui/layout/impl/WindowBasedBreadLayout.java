package org.vaadin.bread.core.ui.layout.impl;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.layout.BreadLayout;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Alejandro Duarte
 */
public class WindowBasedBreadLayout extends AbstractBreadLayout implements BreadLayout {

    protected VerticalLayout mainLayout = new VerticalLayout();
    protected Window formWindow;
    protected String formWindowWidth = "500px";

    public WindowBasedBreadLayout() {
    	super();
        setCompositionRoot(breadComponent);
        mainLayout.setSizeFull();
        mainLayout.setMargin(false);
        mainLayout.setSpacing(true);
        setSizeFull();

        captionLabel.addStyleName(ValoTheme.LABEL_COLORED);
        captionLabel.addStyleName(ValoTheme.LABEL_BOLD);
        captionLabel.addStyleName(ValoTheme.LABEL_H3);
        captionLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        captionLabel.setVisible(false);

        breadComponent.addComponent(toolbarLayout, 2);

        setWindowCaption(CrudOperation.ADD, "Add");
        setWindowCaption(CrudOperation.UPDATE, "Update");
        setWindowCaption(CrudOperation.DELETE, "Are you sure you want to delete this item?");
    }

	protected void addToolbarLayout(CssLayout toolbarLayout) {
		
	}
    
    @Override
    public void addToolbarComponent(Component component) {
        toolbarLayout.setVisible(true);
        toolbarLayout.addComponent(component);
    }

    private void showWindow(String caption, Component form) {
        formWindow = new Window(caption, detailComponent);
        formWindow.setWidth(formWindowWidth);
        formWindow.setModal(true);
        formWindow.center();
        UI.getCurrent().addWindow(formWindow);
    }

    @Override
    public void showForm(CrudOperation operation, Component form) {
    	super.showForm(operation, form);
        if (!operation.equals(CrudOperation.READ)) {
            showWindow(detailCaptions.get(operation), form);
        }
    }

    @Override
    public void hideForm() {
    	super.hideForm();
        if (formWindow != null) {
            formWindow.close();
        }
    }

    public void setWindowCaption(CrudOperation operation, String caption) {
    	setDetailCaption(operation, caption);
    }

    public void setFormWindowWidth(String formWindowWidth) {
        this.formWindowWidth = formWindowWidth;
    }

	public String getFormWindowWidth() {
		return formWindowWidth;
	}

}
