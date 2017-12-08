package org.vaadin.bread.example.repo;

import java.util.List;

public class GroupRepository {

    public static List<Group> findAll() {
        return JPAService.runInTransaction(em ->
                em.createQuery("select g from Group g", Group.class).getResultList()
        );
    }

    public static Group save(Group group) {
        return JPAService.runInTransaction(em -> em.merge(group));
    }

}
