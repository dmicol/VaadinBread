package org.vaadin.bread.core.ui.bread;

import java.util.List;
import java.util.Map;

import org.vaadin.bread.core.ui.form.FormFactory;
import org.vaadin.bread.core.ui.layout.BreadLayout;

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

    void setBreadListener(BreadListener<T> crudListener);

    BreadLayout getBreadLayout();
    
    String getDeletedMessage();
	void setDeletedMessage(String deletedMessage);

	String getSavedMessage();
	void setSavedMessage(String savedMessage);

	String getRowCountCaption();	
	void setRowCountCaption(String rowCountCaption);
	
    List<String> getVisibleProperties();
    void setVisibleProperties(List<String> visibleProperties);

    Map<String, String> getPropertyCaptions();
    void setPropertyCaptions(Map<String, String> fieldCaptions);
}
