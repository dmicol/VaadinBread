package org.vaadin.bread.test.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * @author Alejandro Duarte
 */
public class UserRepository {

    public static List<User> findAll() {
        return JPAService.runInTransaction(em ->
                em.createQuery("select u from User u", User.class).getResultList()
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
