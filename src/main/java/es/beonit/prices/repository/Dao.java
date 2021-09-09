package es.beonit.prices.repository;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.domain.User;

import java.util.List;

public interface Dao {
    public List<Prices> getNotes();
    public List<User> getUsers();
    public int saveNote(Prices note);
    public int deleteNote(Prices note);
    public int updateNote(Prices note);
}
