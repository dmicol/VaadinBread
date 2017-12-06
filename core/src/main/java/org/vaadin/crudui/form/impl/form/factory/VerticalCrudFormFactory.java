/**
 * 
 */
package org.vaadin.crudui.form.impl.form.factory;

import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.Operation;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

/**
 * @author Dmitrij Colautti
 *
 */
public class VerticalCrudFormFactory<T> extends VerticalFormFactory<T, CrudOperation> {

	/**
	 * @param domainType
	 * @param operations
	 */
	public VerticalCrudFormFactory(Class<T> domainType) {
		super(domainType, CrudOperation.values());
		CrudFormFactoryHelper.addButtonDefaults(this);
	}


    protected Layout buildFooter(Operation operation, T domainObject, Button.ClickListener operationButtonClickListener) {
    	HorizontalLayout footerLayout = (HorizontalLayout) super.buildFooter(operation, domainObject, operationButtonClickListener);
    	
        Button cancelButton = buildOperationButton(CrudOperation.CANCEL, null, configurations.get(operation).getOperationListener(CrudOperation.CANCEL)); 
        
        if (cancelButton != null) {
            footerLayout.addComponent(cancelButton);
        }

        return footerLayout;
    }
}
