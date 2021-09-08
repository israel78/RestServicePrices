package es.beonit.prices.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import es.beonit.prices.domain.Note;
import es.beonit.prices.domain.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;



@Repository
public class DaoImpl implements Dao {

    @PersistenceContext
    private EntityManager em;

    public List<Note> getNotes() {
        CriteriaQuery<Note> criteriaQuery = em.getCriteriaBuilder().createQuery(Note.class);
        criteriaQuery.select(criteriaQuery.from(Note.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
    public List<User> getUsers() {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
    @Transactional
    public int saveNote(Note note) {
        Session ses = em.unwrap(Session.class);
        ses.save(note);
        return note.getId();
    }
    @Transactional
    public int updateNote(Note note) {
        Session ses = em.unwrap(Session.class);
        ses.update(note);
        return note.getId();
    }
    @Transactional
    public int deleteNote(Note note) {
        Session ses = em.unwrap(Session.class);
        ses.delete(note);
        return 0;
    }
}