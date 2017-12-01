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
public class GridLayoutCrudFormFactory<T> extends GridLayoutFormFactory<T, CrudOperation> {

	/**
	 * @param domainType
	 * @param operations
	 * @param columns
	 * @param rows
	 */
	public GridLayoutCrudFormFactory(Class<T> domainType, int columns, int rows) {
		super(domainType, CrudOperation.values(), columns, rows);
		
    	CrudFormFactoryHelper.addButtonDefaults(this);
	}


    protected Layout buildFooter(Operation operation, T domainObject, Button.ClickListener operationButtonClickListener) {
    	HorizontalLayout footerLayout = (HorizontalLayout) super.buildFooter(operation, domainObject, operationButtonClickListener);
    	
        Button cancelButton = buildCancelButton(configurations.get(operation).getOperationListener(CrudOperation.CANCEL));

        if (cancelButton != null) {
            footerLayout.addComponent(cancelButton);
        }

        return footerLayout;
    }
}
