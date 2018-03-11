package org.vaadin.bread.core.ui.bread;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.vaadin.bread.core.ui.PropertiesConfiguration;
import org.vaadin.bread.core.ui.form.FormFactory;
import org.vaadin.bread.core.ui.layout.BreadLayout;
import org.vaadin.bread.core.ui.relation.RelationsConfiguration;
import org.vaadin.bread.core.ui.support.BeanExcelBuilder;
import org.vaadin.bread.core.ui.support.ExcelOnDemandStreamResource;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Resource;
import com.vaadin.ui.Composite;

/**
 * @author Alejandro Duarte
 */
public abstract class AbstractBread<T> extends Composite implements Bread<T> {
	private static final long serialVersionUID = -7551473402053967243L;

	protected Class<T> domainType;

    protected PropertiesConfiguration propertiesConfiguration;
    protected RelationsConfiguration relationsConfiguration; 

    protected DataProvider<T, ?> dataProvider;
    protected AddOperationListener<T> addOperation = t -> null;
    protected UpdateOperationListener<T> updateOperation = t -> null;
    protected DeleteOperationListener<T> deleteOperation = t -> { };
    protected Map<String, Resource> exportOperations = new HashMap<>();

    protected BreadLayout breadLayout;
    protected FormFactory<T> crudFormFactory;
    
    protected String rowCountCaption = "%d items(s) found";
    protected String savedMessage = "Item saved";
    protected String deletedMessage = "Item deleted";

    public AbstractBread(Class<T> domainType, BreadLayout crudLayout) {
        this.domainType = domainType;
        this.breadLayout = crudLayout;
                
        exportOperations.put("EXCEL", excelResource());

        setCompositionRoot(crudLayout);
        setSizeFull();

		propertiesConfiguration = new PropertiesConfiguration(domainType);
		relationsConfiguration = new RelationsConfiguration(domainType);
    }
    
    protected Resource excelResource() {
    	return new ExcelOnDemandStreamResource() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -4131280232907628509L;

			@Override
			protected XSSFWorkbook getWorkbook() {
				return new BeanExcelBuilder<T>(domainType).createExcelDocument(dataProvider);
			}
		};
    }

    @Override
    public void setCaption(String caption) {
        breadLayout.setCaption(caption);
    }

    public BreadLayout getBreadLayout() {
        return breadLayout;
    }

    @Override
    public FormFactory<T> getCrudFormFactory() {
        return crudFormFactory;
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
    public void setItemListListener(ItemListListener<T> breadListener) {
    	setDataProvider(breadListener.getDataProvider());
        setAddOperation(breadListener::add);
        setUpdateOperation(breadListener::update);
        setDeleteOperation(breadListener::delete);
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

	public Class<T> getDomainType() {
		return domainType;
	}

	@Override
	public String getRowCountCaption() {
		return rowCountCaption;
	}

	@Override
	public String getSavedMessage() {
		return savedMessage;
	}

	@Override
	public String getDeletedMessage() {
		return deletedMessage;
	}

    @Override
	public void setRowCountCaption(String rowCountCaption) {
        this.rowCountCaption = rowCountCaption;
    }

    @Override
	public void setSavedMessage(String savedMessage) {
        this.savedMessage = savedMessage;
    }
    
    @Override
	public void setDeletedMessage(String deletedMessage) {
        this.deletedMessage = deletedMessage;
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

	@Override
	public PropertiesConfiguration getPropertiesConfiguration() {
		return propertiesConfiguration;
	}

	@Override
	public RelationsConfiguration getRelationsConfiguration() {
		return relationsConfiguration;
	}
}
