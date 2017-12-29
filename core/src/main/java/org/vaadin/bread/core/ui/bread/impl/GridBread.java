package org.vaadin.bread.core.ui.bread.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.vaadin.bread.core.ui.bread.AbstractBread;
import org.vaadin.bread.core.ui.bread.OperationException;
import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.form.FormConfiguration;
import org.vaadin.bread.core.ui.form.impl.form.factory.FormFactoryBuilder;
import org.vaadin.bread.core.ui.layout.BreadLayout;
import org.vaadin.bread.core.ui.layout.impl.WindowBasedBreadLayout;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.data.util.BeanUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;

/**
 * @author Alejandro Duarte
 */
public class GridBread<T> extends AbstractBread<T> {
	
	public static final String EXPORTER_EXCEL = "EXCEL";

    protected Button findAllButton;
    protected Button addButton;
    protected Button updateButton;
    protected Button deleteButton;
    protected Grid<T> grid;

    protected LinkedHashMap<String, Button> exporterButtons = new LinkedHashMap<>();
    private boolean clickRowToUpdate;

    public GridBread(Class<T> domainType) {
        this(domainType, new WindowBasedBreadLayout());
    }

    public GridBread(Class<T> domainType, BreadLayout crudLayout) {
        super(domainType, crudLayout);
        setCrudFormFactory(new FormFactoryBuilder().formLayoutBread(domainType).build());
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

        grid = new Grid<>(domainType);
        grid.setSizeFull();
        grid.addSelectionListener(e -> gridSelectionChanged());
        
		try {
			grid.removeAllColumns();
			List<PropertyDescriptor> descriptors;
			descriptors = BeanUtil.getBeanPropertyDescriptors(domainType);
			descriptors.stream()
			.filter(d -> !d.getName().equals("class"))
			.forEach(pd -> {
				Class<?> propertyType = pd.getPropertyType();
				if (!(propertyType.isArray() || Collection.class.isAssignableFrom(propertyType))) {        			
					grid.addColumn(pd.getName());
				}
			});
		} catch (IntrospectionException e1) {
			throw new RuntimeException(e1);
		}
        breadLayout.setMainComponent(grid);
        
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
        	crudFormFactory.getConfiguration(CrudOperation.READ).setOperationActionListener(CrudOperation.READ, event -> {
                grid.asSingleSelect().clear();
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
                grid.getDataProvider().refreshItem(updatedObject);
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
    	config.setOperationActionListener(operation, operationPerformedClickEvent -> {
            breadLayout.hideForm();
            buttonClickListener.buttonClick(operationPerformedClickEvent);
            Notification.show(successMessage);
        });
    	config.setOperationActionListener(CrudOperation.CANCEL, cancelClickEvent -> {
	            if (clickRowToUpdate) {
	                grid.asSingleSelect().clear();
	            } else {
                    T selected = grid.asSingleSelect().getValue();
                    breadLayout.hideForm();
                    grid.asSingleSelect().clear();
                    grid.asSingleSelect().setValue(selected);
            	}
                });
        Component form = crudFormFactory.buildNewForm(operation
        		, domainObject, readOnly);

        breadLayout.showForm(operation, form);
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
    
    public Button getExporterMenu(String name) {
    	return exporterButtons.get(name);
    }

    public GridBread<T> addExporterMenu(String name, Button exporterButton) {
    	exporterButtons.put(name, exporterButton);
    	new FileDownloader(getExporter(name)).extend(exporterButton);
        breadLayout.addToolbarComponent(exporterButton);

    	return this;
    }

}