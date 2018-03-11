package org.vaadin.bread.core.ui.bread.impl;

import java.util.LinkedHashMap;

import org.vaadin.bread.core.ui.bread.AbstractBread;
import org.vaadin.bread.core.ui.bread.OperationException;
import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.form.FormConfiguration;
import org.vaadin.bread.core.ui.form.impl.form.factory.FormFactoryBuilder;
import org.vaadin.bread.core.ui.form.impl.form.factory.FormLayoutFormFactory;
import org.vaadin.bread.core.ui.itemList.ItemList;
import org.vaadin.bread.core.ui.itemList.impl.GridItemList;
import org.vaadin.bread.core.ui.layout.BreadLayout;
import org.vaadin.bread.core.ui.layout.impl.WindowBasedBreadLayout;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

/**
 * @author Alejandro Duarte
 */
public class GridBread<T> extends AbstractBread<T> {
	private static final long serialVersionUID = 2259353891274839889L;

	public static final String EXPORTER_EXCEL = "EXCEL";

    protected Button findAllButton;
    protected Button addButton;
    protected Button updateButton;
    protected Button deleteButton;
    protected GridItemList<T> gridItemList;

    protected LinkedHashMap<String, Button> exporterButtons = new LinkedHashMap<>();
    private boolean clickRowToUpdate;

    public GridBread(Class<T> domainType) {
        this(domainType, new WindowBasedBreadLayout());
    }

    public GridBread(Class<T> domainType, BreadLayout crudLayout) {
        super(domainType, crudLayout);
        setCrudFormFactory(new FormFactoryBuilder<T, FormLayoutFormFactory<T>>().formLayoutBread(domainType).build());
        initLayout();
    }

    protected void initLayout() {
        findAllButton = new Button("", e -> findAllButtonClicked());
        findAllButton.setDescription("Refresh list");
        findAllButton.setIcon(VaadinIcons.REFRESH);
        breadLayout.addToolbarComponent(findAllButton);
        
        addButton = new Button("", e -> addButtonClicked());
        addButton.setDescription("Add");
        addButton.setIcon(VaadinIcons.PLUS);
        breadLayout.addToolbarComponent(addButton);

        updateButton = new Button("", e -> updateButtonClicked());
        updateButton.setDescription("Update");
        updateButton.setIcon(VaadinIcons.PENCIL);
        breadLayout.addToolbarComponent(updateButton);

        deleteButton = new Button("", e -> deleteButtonClicked());
        deleteButton.setDescription("Delete");
        deleteButton.setIcon(VaadinIcons.TRASH);
        breadLayout.addToolbarComponent(deleteButton);

        gridItemList = new GridItemList<>(domainType);
        gridItemList.setSizeFull();
        gridItemList.addSelectionListener(e -> gridSelectionChanged());
        
        breadLayout.setMainComponent(gridItemList);
        
        Button btn = new Button(FontAwesome.FILE_EXCEL_O.getHtml());
        btn.setCaptionAsHtml(true);
        btn.setDescription("Export to Excel");
        addExporterMenu(EXPORTER_EXCEL, btn);

        updateButtons();
    }

    @Override
    public void attach() {
        super.attach();
        refreshGrid();
    }

    @Override
    public void setAddOperationVisible(boolean visible) {
        addButton.setVisible(visible);
    }

    @Override
    public void setUpdateOperationVisible(boolean visible) {
        updateButton.setVisible(visible);
    }

    @Override
    public void setDeleteOperationVisible(boolean visible) {
        deleteButton.setVisible(visible);
    }

    @Override
    public void setFindAllOperationVisible(boolean visible) {
        findAllButton.setVisible(false);
    }

    public void refreshGrid() {
    	dataProvider.refreshAll();
    }
    
    public void setClickRowToUpdate(boolean clickRowToUpdate) {
        this.clickRowToUpdate = clickRowToUpdate;
    }
    
    @Override
    public void setDataProvider(DataProvider<T, ?> dataProvider) {
    	super.setDataProvider(dataProvider);
    	gridItemList.setDataProvider(dataProvider);
    }

    protected void updateButtons() {
        boolean rowSelected = !gridItemList.asSingleSelect().isEmpty();
        updateButton.setEnabled(rowSelected);
        deleteButton.setEnabled(rowSelected);
    }

    protected void gridSelectionChanged() {
        updateButtons();
        T domainObject = gridItemList.asSingleSelect().getValue();

        if (domainObject != null) {
        	crudFormFactory.getConfiguration(CrudOperation.READ).setOperationActionListener(CrudOperation.READ, event -> {
                gridItemList.asSingleSelect().clear();
            });
            Component form = crudFormFactory.buildNewForm(CrudOperation.READ, domainObject, true);

            breadLayout.showForm(CrudOperation.READ, form);

            if (clickRowToUpdate) {
                updateButtonClicked();
            }

        } else {
            breadLayout.hideForm();
        }
    }

    protected void findAllButtonClicked() {
        gridItemList.asSingleSelect().clear();
        refreshGrid();
        Notification.show(String.format(rowCountCaption, gridItemList.getDataProvider().size(new Query<>())));
    }

    protected void addButtonClicked() {
        T domainObject = getNewDomaiObject();
        showForm(CrudOperation.ADD, domainObject, false, savedMessage, event -> {
            try {
                T addedObject = addOperation.perform(domainObject);
                refreshGrid();
                gridItemList.asSingleSelect().setValue(addedObject);
                // TODO: grid.scrollTo(addedObject);
            } catch (OperationException e1) {
                refreshGrid();
            } catch (Exception e2) {
                refreshGrid();
                throw e2;
            }
        });
    }

    protected T getNewDomaiObject() {
    	try {
			return domainType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    }
    
    protected void updateButtonClicked() {
        T domainObject = gridItemList.asSingleSelect().getValue();
        showForm(CrudOperation.UPDATE, domainObject, false, savedMessage, event -> {
            try {
                T updatedObject = updateOperation.perform(domainObject);
                gridItemList.getDataProvider().refreshItem(updatedObject);
                gridItemList.asSingleSelect().clear();
                gridItemList.asSingleSelect().setValue(updatedObject);
                // TODO: grid.scrollTo(updatedObject);
            } catch (OperationException e1) {
                refreshGrid();
            } catch (Exception e2) {
                refreshGrid();
                throw e2;
            }
        });
    }

    protected void deleteButtonClicked() {
        T domainObject = gridItemList.asSingleSelect().getValue();
        showForm(CrudOperation.DELETE, domainObject, true, deletedMessage, event -> {
            try {
                deleteOperation.perform(domainObject);
                refreshGrid();
                gridItemList.asSingleSelect().clear();
            } catch (OperationException e1) {
                refreshGrid();
            } catch (Exception e2) {
                refreshGrid();
                throw e2;
            }
        });
    }

    protected void showForm(CrudOperation operation, T domainObject, boolean readOnly, String successMessage, Button.ClickListener buttonClickListener) {
    	FormConfiguration config = crudFormFactory.getConfiguration(operation);
    	config.setOperationActionListener(operation, operationPerformedClickEvent -> {
    		breadLayout.hideForm();
			buttonClickListener.buttonClick(operationPerformedClickEvent);
			Notification.show(successMessage);
        });
    	config.setOperationActionListener(CrudOperation.CANCEL, cancelClickEvent -> {
	            if (clickRowToUpdate) {
	                gridItemList.asSingleSelect().clear();
	            } else {
                    T selected = gridItemList.asSingleSelect().getValue();
                    breadLayout.hideForm();
                    gridItemList.asSingleSelect().clear();
                    gridItemList.asSingleSelect().setValue(selected);
            	}
                });
        Component form = crudFormFactory.buildNewForm(operation
        		, domainObject, readOnly);

        breadLayout.showForm(operation, form);
    }

    public Button getFindAllButton() {
        return findAllButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    
    public Button getExporterMenu(String name) {
    	return exporterButtons.get(name);
    }

    public GridBread<T> addExporterMenu(String name, Button exporterButton) {
    	exporterButtons.put(name, exporterButton);
    	new FileDownloader(getExporter(name)).extend(exporterButton);
        breadLayout.addToolbarComponent(exporterButton);

    	return this;
    }

	public ItemList<T> getItemList() {
		return gridItemList;
	}

	public GridItemList<T> getGridItemList() {
		return gridItemList;
	}

}