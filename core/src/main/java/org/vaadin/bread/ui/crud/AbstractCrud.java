package org.vaadin.bread.ui.crud;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.vaadin.bread.ui.form.FormFactory;
import org.vaadin.bread.ui.layout.BreadLayout;
import org.vaadin.bread.ui.support.BeanExcelBuilder;
import org.vaadin.bread.ui.support.ExcelOnDemandStreamResource;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Resource;
import com.vaadin.ui.Composite;

/**
 * @author Alejandro Duarte
 */
public abstract class AbstractCrud<T> extends Composite implements Crud<T> {

    protected Class<T> domainType;

    protected DataProvider<T, ?> dataProvider;
    protected AddOperationListener<T> addOperation = t -> null;
    protected UpdateOperationListener<T> updateOperation = t -> null;
    protected DeleteOperationListener<T> deleteOperation = t -> { };
    protected Map<String, Resource> exportOperations = new HashMap<>();

    protected BreadLayout crudLayout;
    protected FormFactory<T> crudFormFactory;

    public AbstractCrud(Class<T> domainType, BreadLayout crudLayout, FormFactory<T> crudFormFactory) {
        this.domainType = domainType;
        this.crudLayout = crudLayout;
        this.crudFormFactory = crudFormFactory;
                
        exportOperations.put("EXCEL", new ExcelOnDemandStreamResource() {
			
			@Override
			protected XSSFWorkbook getWorkbook() {
				return new BeanExcelBuilder<T>(domainType).createExcelDocument(dataProvider);
			}
		});

        setCompositionRoot(crudLayout);
        setSizeFull();
    }

    @Override
    public void setCaption(String caption) {
        crudLayout.setCaption(caption);
    }

    public BreadLayout getCrudLayout() {
        return crudLayout;
    }

    @Override
    public void setCrudFormFactory(FormFactory<T> crudFormFactory) {
        this.crudFormFactory = crudFormFactory;
    }
    
    @Override
    public void setDataProvider(DataProvider<T, ?> dataProvider) {
    	this.dataProvider = dataProvider;
    }
    
    @Override
    public void setAddOperation(AddOperationListener<T> addOperation) {
        this.addOperation = addOperation;
    }

    @Override
    public void setUpdateOperation(UpdateOperationListener<T> updateOperation) {
        this.updateOperation = updateOperation;
    }

    @Override
    public void setDeleteOperation(DeleteOperationListener<T> deleteOperation) {
        this.deleteOperation = deleteOperation;
    }

    @Override
    public void setOperations(DataProvider<T, ?> dataProvider, AddOperationListener<T> addOperation, UpdateOperationListener<T> updateOperation, DeleteOperationListener<T> deleteOperation) {
        setDataProvider(dataProvider);
        setAddOperation(addOperation);
        setUpdateOperation(updateOperation);
        setDeleteOperation(deleteOperation);
    }

    @Override
    public FormFactory<T> getCrudFormFactory() {
        return crudFormFactory;
    }

    @Override
    public void setCrudListener(CrudListener<T> crudListener) {
    	setDataProvider(crudListener.getDataProvider());
        setAddOperation(crudListener::add);
        setUpdateOperation(crudListener::update);
        setDeleteOperation(crudListener::delete);
    }

    public void addExporter(String name, Resource exporter) {
        exportOperations.put(name, exporter);
    }

    public void removeExporter(String name) {
        exportOperations.remove(name);
    }
    
    public Resource getExporter(String name) {
    	return exportOperations.get(name);
    }

	public DataProvider<T, ?> getDataProvider() {
		return dataProvider;
	}
}
