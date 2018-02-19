/**
 * 
 */
package org.vaadin.bread.core.ui.itemListLayout.impl;

import org.vaadin.bread.core.ui.itemListLayout.ListLayout;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

/**
 * @author Dmitrij Colautti
 *
 */
public abstract class AbstractListLayout extends Composite implements ListLayout{
	private static final long serialVersionUID = 5912512130984239826L;
	
	protected Label captionLabel = new Label();

	public AbstractListLayout() {
		super();
    }

	/**
	 * @param compositionRoot
	 */
	public AbstractListLayout(AbstractComponent compositionRoot) {
		super(compositionRoot);
	}


}