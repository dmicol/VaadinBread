/**
 * 
 */
package org.vaadin.bread.core.ui.itemList;

import org.vaadin.bread.core.ui.bread.AddOperationListener;
import org.vaadin.bread.core.ui.bread.DeleteOperationListener;
import org.vaadin.bread.core.ui.bread.ItemListListener;
import org.vaadin.bread.core.ui.bread.UpdateOperationListener;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.SingleSelect;

/**
 * @author Dmitrij Colautti
 *
 */
public interface ItemList<T> extends Component {
	
    void setDataProvider(DataProvider<T, ?> dataProvider);

	DataProvider<T, ?> getDataProvider();

    void setAddOperation(AddOperationListener<T> addOperation);

    void setUpdateOperation(UpdateOperationListener<T> updateOperation);

    void setDeleteOperation(DeleteOperationListener<T> deleteOperation);

    void setOperations(DataProvider<T, ?> dataProvider, AddOperationListener<T> addOperation, UpdateOperationListener<T> updateOperation, DeleteOperationListener<T> deleteOperation);

    void setItemListListener(ItemListListener<T> crudListener);
    
	String getRowCountCaption();	
	
	void setRowCountCaption(String rowCountCaption);

	/**
	 * @param listener
	 */
	void addSelectionListener(SelectionListener<T> listener);

	/**
	 * @return
	 */
	SingleSelect<T> asSingleSelect();
	
	
}
