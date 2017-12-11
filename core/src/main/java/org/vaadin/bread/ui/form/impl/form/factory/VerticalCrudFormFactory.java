/**
 * 
 */
package org.vaadin.bread.ui.form.impl.form.factory;

import org.vaadin.bread.ui.crud.CrudOperation;
import org.vaadin.bread.ui.crud.Operation;
import org.vaadin.bread.ui.crud.OperationAction;
import org.vaadin.bread.ui.crud.OperationMode;

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


    protected Layout buildFooter(CrudOperation operationMode, T domainObject) {
    	HorizontalLayout footerLayout = (HorizontalLayout) super.buildFooter(operationMode
    			, new OperationAction[] {operationMode, CrudOperation.CANCEL}
    			, domainObject);

        return footerLayout;
    }
}
