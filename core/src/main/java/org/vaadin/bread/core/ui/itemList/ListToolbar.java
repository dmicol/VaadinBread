/**
 * 
 */
package org.vaadin.bread.core.ui.itemList;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;

/**
 * @author Dmitrij Colautti
 *
 */
public class ListToolbar extends CssLayout implements ListOperation{

	private static final long serialVersionUID = 2940369060777753092L;

	
    protected Button findAllButton;
    protected Button addButton;
    protected Button updateButton;
    protected Button deleteButton;
    protected LinkedHashMap<String, Button> exporterButtons = new LinkedHashMap<>();
    protected Map<String, Resource> exportOperations = new HashMap<>();
    
    protected void initLayout() {
        findAllButton = new Button("");
        findAllButton.setDescription("Refresh list");
        findAllButton.setIcon(VaadinIcons.REFRESH);
        addComponent(findAllButton);
        
        addButton = new Button("");
        addButton.setDescription("Add");
        addButton.setIcon(VaadinIcons.PLUS);
        addComponent(addButton);

        updateButton = new Button("");
        updateButton.setDescription("Update");
        updateButton.setIcon(VaadinIcons.PENCIL);
        addComponent(updateButton);

        deleteButton = new Button("");
        deleteButton.setDescription("Delete");
        deleteButton.setIcon(VaadinIcons.TRASH);
        addComponent(deleteButton);

        
        Button btn = new Button(FontAwesome.FILE_EXCEL_O.getHtml());
        btn.setCaptionAsHtml(true);
        btn.setDescription("Export to Excel");
        addExporterMenu(EXPORTER_EXCEL, btn);

    }
    
    public ListToolbar addExporterMenu(String name, Button exporterButton) {
    	exporterButtons.put(name, exporterButton);
    	new FileDownloader(getExporter(name)).extend(exporterButton);
        addComponent(exporterButton);

    	return this;
    }
    
    public Button getExporterMenu(String name) {
    	return exporterButtons.get(name);
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
    
    /* (non-Javadoc)
     * @see org.vaadin.bread.core.ui.itemList.ListOperation#setAddOperationEnabled(boolean)
     */
    @Override
    public void setAddOperationEnabled(boolean enabled) {
    	addButton.setEnabled(enabled);
    }
    
    /* (non-Javadoc)
     * @see org.vaadin.bread.core.ui.itemList.ListOperation#setDeleteOperationEnabled(boolean)
     */
    @Override
    public void setDeleteOperationEnabled(boolean enabled) {
    	deleteButton.setEnabled(enabled);
    }
    
    /* (non-Javadoc)
     * @see org.vaadin.bread.core.ui.itemList.ListOperation#setFindAllOperationEnabled(boolean)
     */
    @Override
    public void setFindAllOperationEnabled(boolean enabled) {
    	findAllButton.setEnabled(enabled);
    }
    
    /* (non-Javadoc)
     * @see org.vaadin.bread.core.ui.itemList.ListOperation#setUpdateOperationEnabled(boolean)
     */
    @Override
    public void setUpdateOperationEnabled(boolean enabled) {
    	updateButton.setEnabled(enabled);
    }

	@Override
	public void addAddClickListener(ClickListener listener) {
		addButton.addClickListener(listener);
	}

	@Override
	public void addDeleteClickListener(ClickListener listener) {
		deleteButton.addClickListener(listener);
	}

	@Override
	public void addFindAllClickListener(ClickListener listener) {
		findAllButton.addClickListener(listener);
	}

	@Override
	public void addUpdateClickListener(ClickListener listener) {
		updateButton.addClickListener(listener);
	}
	
	@Override
    public void addExporter(String name, Resource exporter) {
        exportOperations.put(name, exporter);
    }

	@Override
    public void removeExporter(String name) {
        exportOperations.remove(name);
    }

	@Override
    public Resource getExporter(String name) {
    	return exportOperations.get(name);
    }
}
