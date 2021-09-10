package es.beonit.prices.repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import  java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.domain.User;
import org.springframework.stereotype.Repository;



@Repository
public class DaoImpl implements Dao {

    @PersistenceContext
    private EntityManager em;

    public Prices getPriceResponse(Date dateApply, int productId, int brandId) {
        CriteriaQuery<Prices> criteriaQuery = em.getCriteriaBuilder().createQuery(Prices.class);
        Root root = criteriaQuery.from(Prices.class);
        List<Predicate> conditionsList = new ArrayList<Predicate>();
        Predicate predicateProductId
                = em.getCriteriaBuilder().equal(root.get("productId"), productId);
        Predicate predicateDateApply
                = em.getCriteriaBuilder().between(em.getCriteriaBuilder().literal(dateApply),root.<Date>get("startDate"),root.<Date>get("endDate"));
        Predicate predicateBrandId
                = em.getCriteriaBuilder().equal(root.get("brandId"), brandId);
        conditionsList.add(predicateProductId);
        conditionsList.add(predicateDateApply);
        conditionsList.add(predicateBrandId);
        criteriaQuery.select(root).where(conditionsList.toArray(new Predicate[]{}));
        return em.createQuery(criteriaQuery).getResultList()
                .stream().max(Comparator.comparing(Prices::getPriority))
                .orElse(new Prices());
    }
    public List<User> getUsers() {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
}