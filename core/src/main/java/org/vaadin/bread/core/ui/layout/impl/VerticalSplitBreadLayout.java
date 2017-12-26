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
        mainLayout.setFirstComponent(breadComponent);
        mainLayout.setSecondComponent(detailComponent);
    	
    	addToolbarLayout(toolbarLayout);
        
        detailComponentHeaderLayout.setMargin(new MarginInfo(false, false, true, false));
    }

    @Override
    protected void addToolbarLayout(CssLayout toolbarLayout) {
        browseComponentHeaderLayout.addComponent(toolbarLayout);
    }

    @Override
    public void addToolbarComponent(Component component) {
        if (!browseComponentHeaderLayout.isVisible()) {
            browseComponentHeaderLayout.setVisible(true);
            breadComponent.addComponent(browseComponentHeaderLayout, breadComponent.getComponentCount() - 1);
        }

        toolbarLayout.setVisible(true);
        toolbarLayout.addComponent(component);
    }

}
