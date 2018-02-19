package org.vaadin.bread.core.ui.itemListLayout;

import com.vaadin.ui.Component;

/**
 * @author Alejandro Duarte
 */
public interface ListLayout extends Component {

	void setCaption(String caption);

    void setMainComponent(Component component);

    void setToolbarComponent(Component component);
}
