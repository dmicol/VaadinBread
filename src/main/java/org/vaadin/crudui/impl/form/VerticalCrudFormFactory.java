package org.vaadin.crudui.impl.form;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;
import org.vaadin.crudui.AbstractAutoGeneratedCrudFormFactory;
import org.vaadin.crudui.CrudFormConfiguration;

/**
 * @author Alejandro Duarte
 */
public class VerticalCrudFormFactory<T> extends AbstractAutoGeneratedCrudFormFactory<T> {

    @Override
    public Component buildNewForm(T domainObject, CrudFormConfiguration configuration) {
        Button button = new Button(configuration.getButtonCaption(), configuration.getButtonIcon());
        button.setStyleName(configuration.getButtonStyle());

        HorizontalLayout footerLayout = new HorizontalLayout(button);
        footerLayout.setSizeUndefined();

        FormLayout formLayout = new FormLayout();
        FieldGroup fieldGroup = addFields(domainObject, configuration, formLayout);

        configureButton(configuration, button, fieldGroup);

        VerticalLayout mainLayout = new VerticalLayout(formLayout, footerLayout);
        mainLayout.setComponentAlignment(footerLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setWidth("400px");
        mainLayout.setMargin(true);

        return mainLayout;
    }

}
