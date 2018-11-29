/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author Dmitrij.colautti
 *
 */
public class OperationListFactory {

	public Collection<Operation> retrive(Class<?> clazz) {
		ArrayList<Operation> list = new ArrayList<>();
		
		if (clazz.isPrimitive()) {
			if (clazz == byte.class) {
				clazz = Byte.class;
			} else if (clazz == char.class) {
				clazz = Character.class;
			} else if (clazz == short.class) {
				clazz = Short.class;
			} else if (clazz == int.class) {
				clazz = Integer.class;
			} else if (clazz == long.class) {
				clazz = Long.class;
			} else if (clazz == float.class) {
				clazz = Float.class;
			} else if (clazz == double.class) {
				clazz = Double.class;
			} else if (clazz == boolean.class) {
				clazz = Boolean.class;
			}

		}

		if (String.class.isAssignableFrom(clazz)) {
			list.add(Operation.CONTAINS);
			list.add(Operation.START_WITH);
			list.add(Operation.END_WITH);
			list.add(Operation.NOT_CONTAINS);
			list.add(Operation.EMPTY);
			list.add(Operation.NOT_EMPTY);
			list.add(Operation.EQUALS);
			list.add(Operation.NOT_EQUALS);
			list.add(Operation.GRATER_EQUAL);
			list.add(Operation.LESS_EQUAL);
			list.add(Operation.GRATER_THEN);
			list.add(Operation.LESS_THEN);
			list.add(Operation.IN);
			list.add(Operation.NOT_IN);
			list.add(Operation.RANGE);
		}
		else if (Number.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)) {
			list.add(Operation.EQUALS);
			list.add(Operation.NOT_EQUALS);
			list.add(Operation.GRATER_EQUAL);
			list.add(Operation.LESS_EQUAL);
			list.add(Operation.GRATER_THEN);
			list.add(Operation.LESS_THEN);
			list.add(Operation.EMPTY);
			list.add(Operation.NOT_EMPTY);
			list.add(Operation.IN);
			list.add(Operation.NOT_IN);
			list.add(Operation.RANGE);
		}
		else if (LocalDate.class.isAssignableFrom(clazz) || LocalDateTime.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || Calendar.class.isAssignableFrom(clazz)) {
			list.add(Operation.EQUALS);
			list.add(Operation.NOT_EQUALS);
			list.add(Operation.GRATER_EQUAL);
			list.add(Operation.LESS_EQUAL);
			list.add(Operation.GRATER_THEN);
			list.add(Operation.LESS_THEN);
			list.add(Operation.EMPTY);
			list.add(Operation.NOT_EMPTY);
			list.add(Operation.RANGE);
		}
		else if (Boolean.class.isAssignableFrom(clazz)) {
			list.add(Operation.EQUALS);
			list.add(Operation.NOT_EQUALS);
			list.add(Operation.EMPTY);
			list.add(Operation.NOT_EMPTY);
		}
		else if (Enum.class.isAssignableFrom(clazz)) {
			list.add(Operation.EQUALS);
			list.add(Operation.NOT_EQUALS);
			list.add(Operation.EMPTY);
			list.add(Operation.NOT_EMPTY);
			list.add(Operation.IN);
			list.add(Operation.NOT_IN);
			list.add(Operation.RANGE);
		}
		
		return list;
	}
}
