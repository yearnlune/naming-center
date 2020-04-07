package yearnlune.lab.namingcenter.database.repository.custom.impl;

import yearnlune.lab.namingcenter.database.repository.custom.NamingCustomRepository;
import yearnlune.lab.namingcenter.database.table.Naming;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.10
 * DESCRIPTION :
 */

public class NamingCustomRepositoryImpl implements NamingCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<String> findNames() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = cb.createQuery(String.class);

        Root<Naming> namingRoot = criteriaQuery.from(Naming.class);

        criteriaQuery.select(namingRoot.get("name"));

        TypedQuery<String> typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}
