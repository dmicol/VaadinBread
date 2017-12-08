package org.vaadin.bread.ui.crud;

import org.vaadin.bread.ui.form.FormFactory;
import org.vaadin.bread.ui.layout.CrudLayout;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;

/**
 * @author Alejandro Duarte
 */
public interface Crud<T> extends Component {

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

    void setCrudListener(CrudListener<T> crudListener);

    CrudLayout getCrudLayout();

}
