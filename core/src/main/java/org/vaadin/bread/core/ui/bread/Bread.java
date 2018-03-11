package org.vaadin.bread.core.ui.bread;

import org.vaadin.bread.core.ui.PropertiesConfiguration;
import org.vaadin.bread.core.ui.form.FormFactory;
import org.vaadin.bread.core.ui.layout.BreadLayout;
import org.vaadin.bread.core.ui.relation.RelationsConfiguration;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;

/**
 * @author Alejandro Duarte
 */
public interface Bread<T> extends Component {

    void setAddOperationVisible(boolean visible);

    void setUpdateOperationVisible(boolean visible);

    void setDeleteOperationVisible(boolean visible);

    void setFindAllOperationVisible(boolean visible);

    FormFactory<T> getCrudFormFactory();

    void setCrudFormFactory(FormFactory<T> crudFormFactory);

    void setDataProvider(DataProvider<T, ?> dataProvider);

	DataProvider<T, ?> getDataProvider();

    void setAddOperation(AddOperationListener<T> addOperation);

    void setUpdateOperation(UpdateOperationListener<T> updateOperation);

    void setDeleteOperation(DeleteOperationListener<T> deleteOperation);

    void setOperations(DataProvider<T, ?> dataProvider, AddOperationListener<T> addOperation, UpdateOperationListener<T> updateOperation, DeleteOperationListener<T> deleteOperation);

    void setItemListListener(ItemListListener<T> crudListener);

    BreadLayout getBreadLayout();
    
    String getDeletedMessage();
	void setDeletedMessage(String deletedMessage);

	String getSavedMessage();
	void setSavedMessage(String savedMessage);

	String getRowCountCaption();	
	void setRowCountCaption(String rowCountCaption);

	PropertiesConfiguration getPropertiesConfiguration();

	RelationsConfiguration getRelationsConfiguration();
	
}
