package org.vaadin.bread.core.ui.layout.impl;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalSplitPanel;

/**
 * @author Alejandro Duarte.
 */
public class VerticalSplitBreadLayout extends AbstractTwoComponentsBreadLayout {

    public VerticalSplitBreadLayout() {
    	super();
    	VerticalSplitPanel mainLayout = new VerticalSplitPanel();

    	setCompositionRoot(mainLayout);
        setSizeFull();
    	

        mainLayout.setSizeFull();
        mainLayout.setFirstComponent(firstComponent);
        mainLayout.setSecondComponent(secondComponent);
    	
    	addToolbarLayout(toolbarLayout);
        
        secondComponentHeaderLayout.setMargin(new MarginInfo(false, false, true, false));
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
