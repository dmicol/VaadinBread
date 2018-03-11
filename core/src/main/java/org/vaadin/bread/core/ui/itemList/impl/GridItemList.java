package org.vaadin.bread.core.ui.itemList.impl;

import java.util.LinkedHashMap;

import org.vaadin.bread.core.ui.itemListLayout.ListLayout;
import org.vaadin.bread.core.ui.itemListLayout.impl.VerticalListLayout;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.SingleSelect;

/**
 * @author Alejandro Duarte
 */
public class GridItemList<T> extends AbstractItemList<T> {

	private static final long serialVersionUID = -706819354595679240L;

	protected Grid<T> grid;

    protected LinkedHashMap<String, Button> exporterButtons = new LinkedHashMap<>();
    private boolean clickRowToUpdate;

    public GridItemList(Class<T> domainType) {
        this(domainType, new VerticalListLayout());
    }

    public GridItemList(Class<T> domainType, ListLayout crudLayout) {
        super(domainType, crudLayout);
        initLayout();
    }

    public void initLayout() {
    	super.initLayout();
    	grid = new Grid<>(domainType);
        grid.setSizeFull();
        grid.setColumns((String[]) propertiesConfiguration.getVisibleOrderedProperties().toArray());
        propertiesConfiguration.getPropertiesCaption().forEach((p,c) -> grid.getColumn(p).setCaption(c));
        grid.addSelectionListener(e -> gridSelectionChanged());
        
        listLayout.setMainComponent(grid);
        
        updateButtons();
    }

    @Override
    public void attach() {
        super.attach();
        refreshGrid();
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
        listOperation.setUpdateOperationEnabled(rowSelected);
        listOperation.setDeleteOperationEnabled(rowSelected);
    }

    protected void gridSelectionChanged() {
        updateButtons();
    }

    protected void findAllButtonClicked() {
        grid.asSingleSelect().clear();
        refreshGrid();
        Notification.show(String.format(rowCountCaption, grid.getDataProvider().size(new Query<>())));
    }

    protected T getNewDomaiObject() {
    	try {
			return domainType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    }

    public Grid<T> getGrid() {
        return grid;
    }
    
    public Button getExporterMenu(String name) {
    	return exporterButtons.get(name);
    }

	@Override
	public void addSelectionListener(SelectionListener<T> listener) {
		grid.addSelectionListener(listener);
	}

	@Override
	public SingleSelect<T> asSingleSelect() {
		// TODO Auto-generated method stub
		return grid.asSingleSelect();
	}

}