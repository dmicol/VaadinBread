/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Dmitrij.colautti
 *
 */
public class JpaCriteriaFilterValueFactory {

	public <T> ArrayList<Predicate> build(List<FilterValue> uf, final CriteriaBuilder cb, final Root<T> from) {
		ArrayList<Predicate> ps = new ArrayList<>();
    	
    	uf.stream().forEach(fv-> {
    		
    		Class<?> clazz = fv.getProperty().getPropertyType();

    		clazz = new OperationListFactory().toBoxedClass(clazz);
    		String propName = fv.getProperty().getName();
    		ps.addAll(stringPredicateBuilder(from, cb, propName, fv));

    	});
    	
    	return ps;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> ArrayList<Predicate> stringPredicateBuilder(final Root<T> from, final CriteriaBuilder cb, String propName, FilterValue fv) {		
		ArrayList<Predicate> ps = new ArrayList<>();
		
		Path propPath = from.get(propName);
		Comparable val = (Comparable) fv.getValue1();
		switch (fv.getOperation()) {
		case EQUALS:
			ps.add(cb.equal(propPath, val));
			break;
		case NOT_EQUALS:
			ps.add(cb.notEqual(propPath, val));
			break;
		case CONTAINS:
			ps.add(cb.like(propPath, "%"+val+"%"));
			break;
		case NOT_CONTAINS:
			ps.add(cb.notLike(propPath, "%"+val+"%"));
			break;
		case EMPTY:
			ps.add(cb.isNull(propPath));
			break;
		case NOT_EMPTY:
			ps.add(cb.isNotNull(propPath));
			break;
		case END_WITH:
			ps.add(cb.like(propPath, "%"+val));
			break;
		case START_WITH:
			ps.add(cb.like(propPath, val+"%"));
			break;
		case GRATER_EQUAL:
			ps.add(cb.greaterThanOrEqualTo(propPath, val));
			break;
		case GRATER_THEN:
			ps.add(cb.greaterThan(propPath, val));
			break;
		case LESS_EQUAL:
			ps.add(cb.lessThanOrEqualTo(propPath, val));
			break;
		case LESS_THEN:
			ps.add(cb.lessThan(propPath, val));
			break;
		case RANGE:
			ps.add(cb.between(propPath, val, (Comparable) fv.getValue2()));
			break;
		case NOT_RANGE:
			ps.add(cb.not(cb.between(propPath, val, (Comparable) fv.getValue2())));
			break;
		case IN:
			ps.add(propPath.in(Arrays.asList(((String)val).split(","))));
			break;
		case NOT_IN:
			ps.add(cb.not(propPath.in(Arrays.asList(((String)val).split(",")))));
			break;
		default:
			break;
		}
		
		return ps;
	}
	
	
}
