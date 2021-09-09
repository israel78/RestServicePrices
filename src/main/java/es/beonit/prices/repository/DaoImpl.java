package es.beonit.prices.repository;
import  java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.domain.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;



@Repository
public class DaoImpl implements Dao {

    @PersistenceContext
    private EntityManager em;

    public List<Prices> getNotes() {
        CriteriaQuery<Prices> criteriaQuery = em.getCriteriaBuilder().createQuery(Prices.class);
        criteriaQuery.select(criteriaQuery.from(Prices.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
    public List<User> getUsers() {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
    @Transactional
    public int saveNote(Prices note) {
        Session ses = em.unwrap(Session.class);
        ses.save(note);
        return note.getId();
    }
    @Transactional
    public int updateNote(Prices note) {
        Session ses = em.unwrap(Session.class);
        ses.update(note);
        return note.getId();
    }
    @Transactional
    public int deleteNote(Prices note) {
        Session ses = em.unwrap(Session.class);
        ses.delete(note);
        return 0;
    }
}