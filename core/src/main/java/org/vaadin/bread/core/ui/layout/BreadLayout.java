package org.vaadin.bread.core.ui.layout;

import org.vaadin.bread.core.ui.form.CrudOperation;

import com.vaadin.ui.Component;

/**
 * @author Alejandro Duarte
 */
public interface BreadLayout extends Component {

    void setCaption(String caption);

    void setMainComponent(Component component);

    void addFilterComponent(Component component);

    void addToolbarComponent(Component component);

    void showForm(CrudOperation operation, Component form);

    void hideForm();

}
