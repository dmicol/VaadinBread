package org.vaadin.bread.ui.layout;

import org.vaadin.bread.ui.crud.CrudOperation;

import com.vaadin.ui.Component;

/**
 * @author Alejandro Duarte
 */
public interface CrudLayout extends Component {

    void setCaption(String caption);

    void setMainComponent(Component component);

    void addFilterComponent(Component component);

    void addToolbarComponent(Component component);
    
    Component getToolbar();

    void showForm(CrudOperation operation, Component form);

    void hideForm();

}
