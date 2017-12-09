package org.vaadin.bread.example.base.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.vaadin.bread.example.model.User;
import org.vaadin.bread.example.model.UserFilter;
import org.vaadin.bread.example.model.User_;

public class UserRepository {

    public static List<User> findAll() {
        return JPAService.runInTransaction(em ->
                em.createQuery("select u from User u", User.class).getResultList()
        );
    }

    public static List<User> findAll(UserFilter uf, int offset, int limit) {
    	
    	if (uf==null)
    		return findAll();
    	
        return JPAService.runInTransaction(em -> {
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
	    	CriteriaQuery<User> cq = cb.createQuery(User.class);
	    	Root<User> from = cq.from(User.class);
	    	
	    	cq.select(from);
	    	
	    	
	    	ArrayList<Predicate> ps = new ArrayList<Predicate>();
	    	
	    	if (uf.getName()!=null && !uf.getName().isEmpty()) {
	    		ps.add(cb.like(from.get(User_.name), "%"+uf.getName()+"%"));
	    	}
	    	
	    	if (uf.getBirthDateFrom()!=null ) {
	    		ps.add(cb.greaterThan(from.get(User_.birthDate), uf.getBirthDateFrom()));
	    	}
	    	
	    	if (uf.getBirthDateTo()!=null ) {
	    		ps.add(cb.lessThan(from.get(User_.birthDate), uf.getBirthDateTo()));
	    	}
	    	
	    	if (uf.getPhoneNumber()!=null ) {
	    		ps.add(cb.equal(from.get(User_.phoneNumber), uf.getPhoneNumber()));
	    	}
	    	
	    	if (uf.getEmail()!=null && !uf.getEmail().isEmpty()) {
	    		ps.add(cb.like(from.get(User_.email), "%"+uf.getEmail()+"%"));
	    	}
	    	
	    	if (uf.getActive()!=null ) {
	    		ps.add(cb.equal(from.get(User_.active), uf.getActive()));
	    	}
	    	
	    	if (uf.getMainGroup()!=null ) {
	    		ps.add(cb.equal(from.get(User_.mainGroup), uf.getMainGroup()));
	    	}
	    	
	    	if (uf.getGender()!=null ) {
	    		ps.add(cb.equal(from.get(User_.gender), uf.getGender()));
	    	}
	    	
	    	cq.where(ps.toArray(new Predicate[] {}));
	    	TypedQuery<User> q = em.createQuery(cq);
	    	q.setFirstResult(offset);
	    	q.setMaxResults(limit);
	    	
	        return q.getResultList();
    	}
        );
    }

    public static User save(User user) {
        return JPAService.runInTransaction(em -> em.merge(user));
    }

    public static void delete(User user) {
        JPAService.runInTransaction(em -> {
            em.remove(getById(user.getId(), em));
            return null;
        });
    }

    private static User getById(Long id, EntityManager em) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.id=:id", User.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst().orElse(null);
    }

}
