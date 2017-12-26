package org.vaadin.bread.core.ui.layout.impl;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;

/**
 * @author Alejandro Duarte.
 */
public class HorizontalSplitBreadLayout extends AbstractTwoComponentsBreadLayout {

	protected HorizontalSplitPanel mainLayout = new HorizontalSplitPanel();
	
    public HorizontalSplitBreadLayout() {
    	setCompositionRoot(mainLayout);
        setSizeFull();
    	
    	mainLayout.setSizeFull();
    	mainLayout.setFirstComponent(breadComponent);
    	mainLayout.setSecondComponent(detailComponent);
    	mainLayout.setSplitPosition(60, Sizeable.Unit.PERCENTAGE);
    	
    	addToolbarLayout(toolbarLayout);
    	
        detailComponentHeaderLayout.setMargin(new MarginInfo(false, false, false, true));
    }

    @Override
    protected void addToolbarLayout(CssLayout toolbarLayout) {
        detailComponentHeaderLayout.addComponent(toolbarLayout);
    }

    @Override
    public void addToolbarComponent(Component component) {
        if (!detailComponentHeaderLayout.isVisible()) {
            detailComponentHeaderLayout.setVisible(true);
            detailComponent.addComponent(detailComponentHeaderLayout, detailComponent.getComponentCount() - 1);
        }

        toolbarLayout.setVisible(true);
        toolbarLayout.addComponent(component);
    }

}
