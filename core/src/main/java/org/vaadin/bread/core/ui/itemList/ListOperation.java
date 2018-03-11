package org.vaadin.bread.core.ui.itemList;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;

/**
 * @author Dmitrij Colautti
 */
public interface ListOperation {

	public static final String EXPORTER_EXCEL = "EXCEL";

    void setAddOperationVisible(boolean visible);
    void setUpdateOperationVisible(boolean visible);
    void setDeleteOperationVisible(boolean visible);
    void setFindAllOperationVisible(boolean visible);
    
    void setAddOperationEnabled(boolean enabled);
    void setUpdateOperationEnabled(boolean enabled);
    void setDeleteOperationEnabled(boolean enabled);
    void setFindAllOperationEnabled(boolean enabled);
	/**
	 * @param listener
	 */
	void addAddClickListener(ClickListener listener);

	/**
	 * @param listener
	 */
	void addDeleteClickListener(ClickListener listener);

	/**
	 * @param listener
	 */
	void addFindAllClickListener(ClickListener listener);

	/**
	 * @param listener
	 */
	void addUpdateClickListener(ClickListener listener);

	/**
	 * @param name
	 * @param exporter
	 */
	void addExporter(String name, Resource exporter);

	/**
	 * @param name
	 */
	void removeExporter(String name);

	/**
	 * @param name
	 * @return
	 */
	Resource getExporter(String name);
	/**
	 * 
	 */
	void initLayout();

}
