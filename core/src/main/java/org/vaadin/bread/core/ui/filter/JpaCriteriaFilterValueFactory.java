/**
 * 
 */
package org.vaadin.bread.core.ui.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
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
    		switch (fv.getOperation()) {
			case EQUALS:
				ps.add(cb.equal(from.get(fv.getProperty().getName()), fv.getValue1()));
				break;
			case NOT_EQUALS:
				ps.add(cb.notEqual(from.get(fv.getProperty().getName()), fv.getValue1()));
				break;
			case CONTAINS:
				ps.add(cb.like(from.get(fv.getProperty().getName()), "%"+fv.getValue1()+"%"));
				break;
			case NOT_CONTAINS:
				ps.add(cb.notLike(from.get(fv.getProperty().getName()), "%"+fv.getValue1()+"%"));
				break;
			case EMPTY:
				ps.add(cb.isNull(from.get(fv.getProperty().getName())));
				break;
			case NOT_EMPTY:
				ps.add(cb.isNotNull(from.get(fv.getProperty().getName())));
				break;
			case END_WITH:
				ps.add(cb.like(from.get(fv.getProperty().getName()), "%"+fv.getValue1()));
				break;
			case START_WITH:
				ps.add(cb.like(from.get(fv.getProperty().getName()), fv.getValue1()+"%"));
				break;
			case GRATER_EQUAL:
				ps.add(cb.ge(from.get(fv.getProperty().getName()), (Number)fv.getValue1()));
				break;
			case GRATER_THEN:
				ps.add(cb.gt(from.get(fv.getProperty().getName()), (Number)fv.getValue1()));
				break;
			case LESS_EQUAL:
				ps.add(cb.le(from.get(fv.getProperty().getName()), (Number)fv.getValue1()));
				break;
			case LESS_THEN:
				ps.add(cb.lt(from.get(fv.getProperty().getName()), (Number)fv.getValue1()));
				break;
//			case RANGE:
//				Object v1 = fv.getValue1();
//				Object v2 = fv.getValue2();
//				ps.add(cb.between((Expression<? extends Object>)from.<Object>get(fv.getProperty().getName()), v1, v2));
//				break;
//			case NOT_RANGE:
//				ps.add(cb.not(cb.between(from.get(fv.getProperty().getName()), fv.getValue1(), fv.getValue2())));
//				break;
			default:
				break;
			}
    	});
    	
    	return ps;
	}
	
//	private <T> Expression<? extends T> between (final CriteriaBuilder cb, Expression<T> prop, T v1, T v2) {
//		return cb.between(prop, v1, v2);
//	}

}
