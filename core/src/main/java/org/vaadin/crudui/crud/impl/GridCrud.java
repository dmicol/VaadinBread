package org.vaadin.crudui.crud.impl;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.vaadin.crudui.crud.AbstractCrud;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.OperationException;
import org.vaadin.crudui.form.FormConfiguration;
import org.vaadin.crudui.form.FormFactory;
import org.vaadin.crudui.form.impl.form.factory.VerticalFormFactory;
import org.vaadin.crudui.layout.CrudLayout;
import org.vaadin.crudui.layout.impl.WindowBasedCrudLayout;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;

/**
 * @author Alejandro Duarte
 */
@SuppressWarnings("deprecation")
public class GridCrud<T> extends AbstractCrud<T> {

    protected String rowCountCaption = "%d items(s) found";
    protected String savedMessage = "Item saved";
    protected String deletedMessage = "Item deleted";

    protected Button findAllButton;
    protected Button addButton;
    protected Button updateButton;
    protected Button deleteButton;
    protected Grid<T> grid;

    protected LinkedHashMap<String, Button> exporterButtons = new LinkedHashMap<>();
    protected Collection<T> items;

    public GridCrud(Class<T> domainType) {
        this(domainType, new WindowBasedCrudLayout(), new VerticalFormFactory<>(domainType, CrudOperation.values()));
    }

    public GridCrud(Class<T> domainType, CrudLayout crudLayout) {
        this(domainType, crudLayout, new VerticalFormFactory<>(domainType, CrudOperation.values()));
    }

    public GridCrud(Class<T> domainType, FormFactory<T> crudFormFactory) {
        this(domainType, new WindowBasedCrudLayout(), crudFormFactory);
    }

    public GridCrud(Class<T> domainType, CrudLayout crudLayout, FormFactory<T> crudFormFactory) {
        super(domainType, crudLayout, crudFormFactory);
        initLayout();
    }

    protected void initLayout() {
        findAllButton = new Button("", e -> findAllButtonClicked());
        findAllButton.setDescription("Refresh list");
        findAllButton.setIcon(VaadinIcons.REFRESH);
        crudLayout.addToolbarComponent(findAllButton);
        
        addButton = new Button("", e -> addButtonClicked());
        addButton.setDescription("Add");
        addButton.setIcon(VaadinIcons.PLUS);
        crudLayout.addToolbarComponent(addButton);

        updateButton = new Button("", e -> updateButtonClicked());
        updateButton.setDescription("Update");
        updateButton.setIcon(VaadinIcons.PENCIL);
        crudLayout.addToolbarComponent(updateButton);

        deleteButton = new Button("", e -> deleteButtonClicked());
        deleteButton.setDescription("Delete");
        deleteButton.setIcon(VaadinIcons.TRASH);
        crudLayout.addToolbarComponent(deleteButton);

        grid = new Grid<>(domainType);
        grid.setSizeFull();
        grid.addSelectionListener(e -> gridSelectionChanged());
        crudLayout.setMainComponent(grid);
        
        Button btn = new Button(com.vaadin.server.FontAwesome.FILE_EXCEL_O.getHtml());
        btn.setCaptionAsHtml(true);
        addExporterMenu("EXCEL", btn);

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
    
    @Override
    public void setDataProvider(DataProvider<T, ?> dataProvider) {
    	super.setDataProvider(dataProvider);
    	grid.setDataProvider(dataProvider);
    }

    protected void updateButtons() {
        boolean rowSelected = !grid.asSingleSelect().isEmpty();
        updateButton.setEnabled(rowSelected);
        deleteButton.setEnabled(rowSelected);
    }

    protected void gridSelectionChanged() {
        updateButtons();
        T domainObject = grid.asSingleSelect().getValue();

        if (domainObject != null) {
        	crudFormFactory.getConfiguration(CrudOperation.READ).setOperationListener(CrudOperation.READ, event -> {
                grid.asSingleSelect().clear();
            });
            Component form = crudFormFactory.buildNewForm(CrudOperation.READ, domainObject, true);

            crudLayout.showForm(CrudOperation.READ, form);
        } else {
            crudLayout.hideForm();
        }
    }

    protected void findAllButtonClicked() {
        grid.asSingleSelect().clear();
        refreshGrid();
        Notification.show(String.format(rowCountCaption, grid.getDataProvider().size(new Query())));
    }

    protected void addButtonClicked() {
        try {
            T domainObject = domainType.newInstance();
            showForm(CrudOperation.ADD, domainObject, false, savedMessage, event -> {
                try {
                    T addedObject = addOperation.perform(domainObject);
                    refreshGrid();
                    grid.asSingleSelect().setValue(addedObject);
                    // TODO: grid.scrollTo(addedObject);
                } catch (OperationException e1) {
                    refreshGrid();
                } catch (Exception e2) {
                    refreshGrid();
                    throw e2;
                }
            });
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void updateButtonClicked() {
        T domainObject = grid.asSingleSelect().getValue();
        showForm(CrudOperation.UPDATE, domainObject, false, savedMessage, event -> {
            try {
                T updatedObject = updateOperation.perform(domainObject);
                grid.asSingleSelect().clear();
                refreshGrid();
                grid.asSingleSelect().setValue(updatedObject);
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
        T domainObject = grid.asSingleSelect().getValue();
        showForm(CrudOperation.DELETE, domainObject, true, deletedMessage, event -> {
            try {
                deleteOperation.perform(domainObject);
                refreshGrid();
                grid.asSingleSelect().clear();
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
    	config.setOperationListener(operation, operationPerformedClickEvent -> {
            crudLayout.hideForm();
            buttonClickListener.buttonClick(operationPerformedClickEvent);
            Notification.show(successMessage);
        });
    	config.setOperationListener(CrudOperation.CANCEL, cancelClickEvent -> {
                    T selected = grid.asSingleSelect().getValue();
                    crudLayout.hideForm();
                    grid.asSingleSelect().clear();
                    grid.asSingleSelect().setValue(selected);
                });
        Component form = crudFormFactory.buildNewForm(operation, domainObject, readOnly);

        crudLayout.showForm(operation, form);
    }

    public Grid<T> getGrid() {
        return grid;
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

    public void setRowCountCaption(String rowCountCaption) {
        this.rowCountCaption = rowCountCaption;
    }

    public void setSavedMessage(String savedMessage) {
        this.savedMessage = savedMessage;
    }
    
    public void setDeletedMessage(String deletedMessage) {
        this.deletedMessage = deletedMessage;
    }
    
    public Button getExporterMenu(String name) {
    	return exporterButtons.get(name);
    }

    public GridCrud<T> addExporterMenu(String name, Button exporterButton) {
    	exporterButtons.put(name, exporterButton);
    	new FileDownloader(getExporter(name)).extend(exporterButton);
        crudLayout.addToolbarComponent(exporterButton);

    	return this;
    }
}