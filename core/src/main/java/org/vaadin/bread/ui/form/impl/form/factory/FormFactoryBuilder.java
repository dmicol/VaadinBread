/**
 * 
 */
package org.vaadin.bread.ui.form.impl.form.factory;

import java.util.Arrays;

import org.vaadin.bread.ui.crud.CrudOperation;
import org.vaadin.bread.ui.crud.FilterOperation;
import org.vaadin.bread.ui.crud.OperationMode;
import org.vaadin.bread.ui.form.FormFactory;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Dmitrij Colautti
 *
 */
public class FormFactoryBuilder<T, FFT extends FormFactory<T>> {
	
	private FormFactory<T> ff = null;
	

	private static OperationMode[] crudOperationModes = new OperationMode[] {CrudOperation.ADD, CrudOperation.DELETE, CrudOperation.READ, CrudOperation.UPDATE};
	private static OperationMode[] filterOperationModes = new OperationMode[] {FilterOperation.APPLY};
	
	@SuppressWarnings("unchecked")
	public FFT build() {
		return (FFT) ff;
	}
	
	@SuppressWarnings("unchecked")
	public FormFactoryBuilder<T, GridLayoutFormFactory<T>> gridLayout(Class<T> domainType, OperationMode[] operationModes, int columns, int rows) {
		ff = new GridLayoutFormFactory<T>(domainType, operationModes, columns, rows);
		return (FormFactoryBuilder<T, GridLayoutFormFactory<T>>) this;
	}

	@SuppressWarnings("unchecked")
	public FormFactoryBuilder<T, FormLayoutFormFactory<T>> formLayout(Class<T> domainType, OperationMode[] operationModes ) {
		ff = new FormLayoutFormFactory<T>(domainType, operationModes);
		addCrudDefaults();
		return (FormFactoryBuilder<T, FormLayoutFormFactory<T>>) this;
	}
	
	@SuppressWarnings("unchecked")
	public FormFactoryBuilder<T, GridLayoutFormFactory<T>> gridLayoutBread(Class<T> domainType, int columns, int rows) {
		ff = new GridLayoutFormFactory<T>(domainType
				, crudOperationModes
				, columns, rows);
		addCrudDefaults();
		return (FormFactoryBuilder<T, GridLayoutFormFactory<T>>) this;
	}	

	@SuppressWarnings("unchecked")
	public FormFactoryBuilder<T, FormLayoutFormFactory<T>> formLayoutBread(Class<T> domainType) {
		ff = new FormLayoutFormFactory<T>(domainType
				, crudOperationModes);
		addCrudDefaults();
		return (FormFactoryBuilder<T, FormLayoutFormFactory<T>>) this;
	}
	
	@SuppressWarnings("unchecked")
	public FormFactoryBuilder<T, GridLayoutFormFactory<T>> gridLayoutFilter(Class<T> domainType, int columns, int rows) {
		ff = new GridLayoutFormFactory<T>(domainType
				, filterOperationModes
				, columns, rows);
		addFilterDefaults();
		return (FormFactoryBuilder<T, GridLayoutFormFactory<T>>) this;
	}	

	@SuppressWarnings("unchecked")
	public FormFactoryBuilder<T, FormLayoutFormFactory<T>> formLayoutFilter(Class<T> domainType) {
		ff = new FormLayoutFormFactory<T>(domainType
				, filterOperationModes);
		addFilterDefaults();
		return (FormFactoryBuilder<T, FormLayoutFormFactory<T>>) this;
	}

    private void addFilterDefaults() {
    	ff.getConfiguration(FilterOperation.APPLY)
    		.setOperationActions(Arrays.asList(FilterOperation.APPLY, FilterOperation.EMPTY));
     }
    
    private void addCrudDefaults() {
    	ff.getConfiguration(CrudOperation.ADD).setOperationActions(Arrays.asList(CrudOperation.ADD, CrudOperation.CANCEL));
    	ff.getConfiguration(CrudOperation.DELETE).setOperationActions(Arrays.asList(CrudOperation.DELETE, CrudOperation.CANCEL));
    	ff.getConfiguration(CrudOperation.READ).setOperationActions(Arrays.asList(CrudOperation.READ, CrudOperation.CANCEL));
    	ff.getConfiguration(CrudOperation.UPDATE).setOperationActions(Arrays.asList(CrudOperation.UPDATE, CrudOperation.CANCEL));
		
    	
    	ff.setButtonCaption(CrudOperation.READ, "Ok");
    	ff.setButtonCaption(CrudOperation.ADD, "Add");
    	ff.setButtonCaption(CrudOperation.UPDATE, "Update");
    	ff.setButtonCaption(CrudOperation.DELETE, "Yes, delete");

    	ff.setButtonIcon(CrudOperation.READ, null);
    	ff.setButtonIcon(CrudOperation.ADD, VaadinIcons.CHECK);
    	ff.setButtonIcon(CrudOperation.UPDATE, VaadinIcons.CHECK);
    	ff.setButtonIcon(CrudOperation.DELETE, VaadinIcons.TRASH);

    	ff.addButtonStyleName(CrudOperation.READ, null);
    	ff.addButtonStyleName(CrudOperation.ADD, ValoTheme.BUTTON_PRIMARY);
    	ff.addButtonStyleName(CrudOperation.UPDATE, ValoTheme.BUTTON_PRIMARY);
    	ff.addButtonStyleName(CrudOperation.DELETE, ValoTheme.BUTTON_DANGER);
    }
}
