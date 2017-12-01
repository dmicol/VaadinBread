/**
 * 
 */
package org.vaadin.crudui.crud;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dmitrij Colautti
 *
 */
public interface Operation {
	static Set<Operation> map =  new TreeSet<Operation> () {
		{
			add(FilterOperation.APPLY);
			add(FilterOperation.EMPTY);
			add(CrudOperation.ADD);
			add(CrudOperation.CANCEL);
			add(CrudOperation.DELETE);
			add(CrudOperation.READ);
			add(CrudOperation.UPDATE);
		}
	};
		   
	public static void addNewOperation(Operation operation) {
		if (!map.contains(operation)) {
			map.add(operation);
		}
	}
	
	public static Collection<Operation> operations() {
		return map;
	}
	
	public String getOperationName();
}
