package org.vaadin.bread.core.ui.layout.impl;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Alejandro Duarte.
 */
public class VerticalBreadLayout extends AbstractTwoComponentsBreadLayout {
    
	public VerticalBreadLayout() {
    	super();

    	VerticalLayout mainLayout =  new VerticalLayout();
    	setCompositionRoot(mainLayout);
        setSizeFull();

        mainLayout.addComponents(firstComponent, secondComponent);
        mainLayout.setMargin(false);
    	
    	addToolbarLayout(toolbarLayout);
    }

    @Override
    protected void addToolbarLayout(CssLayout toolbarLayout) {
        firstComponentHeaderLayout.addComponent(toolbarLayout);
    }

    @Override
    public void addToolbarComponent(Component component) {
        if (!firstComponentHeaderLayout.isVisible()) {
            firstComponentHeaderLayout.setVisible(true);
            firstComponent.addComponent(firstComponentHeaderLayout, firstComponent.getComponentCount() - 1);
        }

        toolbarLayout.setVisible(true);
        toolbarLayout.addComponent(component);
    }

}
