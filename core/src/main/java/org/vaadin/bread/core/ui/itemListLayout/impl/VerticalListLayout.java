/**
 * 
 */
package org.vaadin.bread.core.ui.itemListLayout.impl;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Dmitrij Colautti
 *
 */
public class VerticalListLayout extends AbstractListLayout {
	
	private static final long serialVersionUID = -8460063052520969269L;
	
	protected VerticalLayout mainLayout = new VerticalLayout();
	protected boolean toolbarOnTop = true;
	
	public VerticalListLayout() {
		super();
		
    	setCompositionRoot(mainLayout);
        setSizeFull();

        mainLayout.setMargin(false);
	}
	
	/* (non-Javadoc)
	 * @see org.vaadin.bread.core.ui.itemListLayout.ListLayout#setMainComponent(com.vaadin.ui.Component)
	 */
	@Override
	public void setMainComponent(Component component) {
		if (toolbarOnTop) {
			mainLayout.addComponent(component);
		} else {
			mainLayout.addComponentAsFirst(component);
		}		
	}

	/* (non-Javadoc)
	 * @see org.vaadin.bread.core.ui.itemListLayout.ListLayout#setToolbarComponent(com.vaadin.ui.Component)
	 */
	@Override
	public void setToolbarComponent(Component component) {
		if (toolbarOnTop) {
			mainLayout.addComponentAsFirst(component);
		} else {
			mainLayout.addComponent(component);
		}		
	}

}
