/**
 * 
 */
package org.vaadin.crudui.form.impl.form.factory;

import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.AbstractAutoGeneratedFormFactory;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Dmitrij Colautti
 *
 */
public class CrudFormFactoryHelper {

    public static void addButtonDefaults(AbstractAutoGeneratedFormFactory  formFactory) {
    	formFactory.setButtonCaption(CrudOperation.READ, "Ok");
    	formFactory.setButtonCaption(CrudOperation.ADD, "Add");
    	formFactory.setButtonCaption(CrudOperation.UPDATE, "Update");
    	formFactory.setButtonCaption(CrudOperation.DELETE, "Yes, delete");

    	formFactory.setButtonIcon(CrudOperation.READ, null);
    	formFactory.setButtonIcon(CrudOperation.ADD, VaadinIcons.CHECK);
    	formFactory.setButtonIcon(CrudOperation.UPDATE, VaadinIcons.CHECK);
    	formFactory.setButtonIcon(CrudOperation.DELETE, VaadinIcons.TRASH);

    	formFactory.addButtonStyleName(CrudOperation.READ, null);
    	formFactory.addButtonStyleName(CrudOperation.ADD, ValoTheme.BUTTON_PRIMARY);
    	formFactory.addButtonStyleName(CrudOperation.UPDATE, ValoTheme.BUTTON_PRIMARY);
    	formFactory.addButtonStyleName(CrudOperation.DELETE, ValoTheme.BUTTON_DANGER);

    }
}