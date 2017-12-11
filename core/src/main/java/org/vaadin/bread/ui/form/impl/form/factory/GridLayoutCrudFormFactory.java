/**
 * 
 */
package org.vaadin.bread.ui.form.impl.form.factory;

import java.util.Arrays;

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
public class GridLayoutCrudFormFactory<T> extends GridLayoutFormFactory<T, CrudOperation> {

	public GridLayoutCrudFormFactory(Class<T> domainType, int columns, int rows) {
		super(domainType
				, new OperationMode[] {CrudOperation.ADD, CrudOperation.DELETE, CrudOperation.READ, CrudOperation.UPDATE} 
				, columns, rows);
		
		
		getConfiguration(CrudOperation.ADD).setOperationActions(Arrays.asList(CrudOperation.ADD, CrudOperation.CANCEL));
		getConfiguration(CrudOperation.DELETE).setOperationActions(Arrays.asList(CrudOperation.DELETE, CrudOperation.CANCEL));
		getConfiguration(CrudOperation.READ).setOperationActions(Arrays.asList(CrudOperation.READ, CrudOperation.CANCEL));
		getConfiguration(CrudOperation.UPDATE).setOperationActions(Arrays.asList(CrudOperation.UPDATE, CrudOperation.CANCEL));
		
    	CrudFormFactoryHelper.addButtonDefaults(this);
	}


	protected Layout buildFooter(CrudOperation operationMode, T domainObject) {
    	HorizontalLayout footerLayout = (HorizontalLayout) super.buildFooter(operationMode
    			, new OperationAction[] {operationMode, CrudOperation.CANCEL}
    			, domainObject);

        return footerLayout;
    }
}
