package org.vaadin.bread.example.base.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.vaadin.bread.example.model.Group;
import org.vaadin.bread.example.model.GroupFilter;
import org.vaadin.bread.example.model.Group_;

public class GroupRepository {

    public static List<Group> findAll() {
        return JPAService.runInTransaction(em ->
                em.createQuery("select g from Group g", Group.class).getResultList()
        );
    }
    

    public static List<Group> findAll(GroupFilter uf, int offset, int limit) {
    	
    	if (uf==null)
    		return findAll();
    	
        return JPAService.runInTransaction(em -> {
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
	    	CriteriaQuery<Group> cq = cb.createQuery(Group.class);
	    	Root<Group> from = cq.from(Group.class);
	    	
	    	cq.select(from);
	    	
	    	
	    	ArrayList<Predicate> ps = new ArrayList<Predicate>();
	    	
	    	if (uf.getName()!=null && !uf.getName().isEmpty()) {
	    		ps.add(cb.like(from.get(Group_.name), "%"+uf.getName()+"%"));
	    	}
	    	
	    	if (uf.getAdmin()!=null ) {
	    		ps.add(cb.equal(from.get(Group_.admin), uf.getAdmin()));
	    	}
	    	
	    	cq.where(ps.toArray(new Predicate[] {}));
	    	TypedQuery<Group> q = em.createQuery(cq);
	    	q.setFirstResult(offset);
	    	q.setMaxResults(limit);
	    	
	        return q.getResultList();
    	}
        );
    }


    public static Group save(Group group) {
        return JPAService.runInTransaction(em -> em.merge(group));
    }

    public static void delete(Group group) {
        JPAService.runInTransaction(em -> {
            em.remove(getById(group.getId(), em));
            return null;
        });
    }

    private static Group getById(Long id, EntityManager em) {
        TypedQuery<Group> query = em.createQuery("select u from Group u where u.id=:id", Group.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst().orElse(null);
    }

}
