package org.vaadin.bread.ui.layout.impl;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;

/**
 * @author Alejandro Duarte.
 */
public class HorizontalSplitCrudLayout extends AbstractTwoComponentsCrudLayout {

	protected HorizontalSplitPanel mainLayout = new HorizontalSplitPanel();
	
    public HorizontalSplitCrudLayout() {
    	setCompositionRoot(mainLayout);
        setSizeFull();
    	
    	mainLayout.setSizeFull();
    	mainLayout.setFirstComponent(firstComponent);
    	mainLayout.setSecondComponent(secondComponent);
    	mainLayout.setSplitPosition(60, Sizeable.Unit.PERCENTAGE);
    	
    	addToolbarLayout(toolbarLayout);
    	
        secondComponentHeaderLayout.setMargin(new MarginInfo(false, false, false, true));
    }

    @Override
    protected void addToolbarLayout(CssLayout toolbarLayout) {
        secondComponentHeaderLayout.addComponent(toolbarLayout);
    }

    @Override
    public void addToolbarComponent(Component component) {
        if (!secondComponentHeaderLayout.isVisible()) {
            secondComponentHeaderLayout.setVisible(true);
            secondComponent.addComponent(secondComponentHeaderLayout, secondComponent.getComponentCount() - 1);
        }

        toolbarLayout.setVisible(true);
        toolbarLayout.addComponent(component);
    }

}
