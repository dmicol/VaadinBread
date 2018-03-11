package org.vaadin.bread.core.ui.itemList.impl;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.vaadin.bread.core.ui.PropertiesConfiguration;
import org.vaadin.bread.core.ui.bread.AddOperationListener;
import org.vaadin.bread.core.ui.bread.DeleteOperationListener;
import org.vaadin.bread.core.ui.bread.ItemListListener;
import org.vaadin.bread.core.ui.bread.UpdateOperationListener;
import org.vaadin.bread.core.ui.itemList.ItemList;
import org.vaadin.bread.core.ui.itemList.ListOperation;
import org.vaadin.bread.core.ui.itemList.ListToolbar;
import org.vaadin.bread.core.ui.itemListLayout.ListLayout;
import org.vaadin.bread.core.ui.support.BeanExcelBuilder;
import org.vaadin.bread.core.ui.support.ExcelOnDemandStreamResource;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Resource;
import com.vaadin.ui.Composite;

/**
 * @author Alejandro Duarte
 */
public abstract class AbstractItemList<T> extends Composite implements ItemList<T> {
	private static final long serialVersionUID = -7551473402053967243L;
	
	protected Class<T> domainType;

    protected PropertiesConfiguration propertiesConfiguration;

    protected DataProvider<T, ?> dataProvider;
    protected AddOperationListener<T> addOperation = null;    
    protected UpdateOperationListener<T> updateOperation = null;
    protected DeleteOperationListener<T> deleteOperation = null;
    
    protected String rowCountCaption = "%d items(s) found";    

	protected ListLayout listLayout;
	protected ListOperation listOperation = new ListToolbar();

    public AbstractItemList(Class<T> domainType, ListLayout listLayout) {
        this.domainType = domainType;
        this.listLayout = listLayout;
        
        setCompositionRoot(listLayout);                

        setSizeFull();
        propertiesConfiguration = new PropertiesConfiguration(domainType);
    }
    
    public void initLayout() {
    	listOperation.addExporter(ListOperation.EXPORTER_EXCEL, excelResource());
    	listOperation.initLayout();
    }
    
    protected Resource excelResource() {
    	return new ExcelOnDemandStreamResource() {

			private static final long serialVersionUID = -4131280232907628509L;

			@Override
			protected XSSFWorkbook getWorkbook() {
				return new BeanExcelBuilder<T>(domainType).createExcelDocument(dataProvider);
			}
		};
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
    public void setItemListListener(ItemListListener<T> breadListener) {
    	setDataProvider(breadListener.getDataProvider());
        setAddOperation(breadListener::add);
        setUpdateOperation(breadListener::update);
        setDeleteOperation(breadListener::delete);
    }

	public DataProvider<T, ?> getDataProvider() {
		return dataProvider;
	}

	public Class<T> getDomainType() {
		return domainType;
	}

	@Override
	public String getRowCountCaption() {
		return rowCountCaption;
	}

    @Override
	public void setRowCountCaption(String rowCountCaption) {
        this.rowCountCaption = rowCountCaption;
    }

	public AddOperationListener<T> getAddOperation() {
		return addOperation;
	}

	public UpdateOperationListener<T> getUpdateOperation() {
		return updateOperation;
	}

	public DeleteOperationListener<T> getDeleteOperation() {
		return deleteOperation;
	}

	public ListOperation getListOperation() {
		return listOperation;
	}

	public void setListOperation(ListOperation listOperation) {
		this.listOperation = listOperation;
	}
}
