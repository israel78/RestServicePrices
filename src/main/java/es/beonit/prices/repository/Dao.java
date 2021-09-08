package es.beonit.prices.repository;

import es.beonit.prices.domain.Note;
import es.beonit.prices.domain.User;

import java.util.List;

public interface Dao {
    public List<Note> getNotes();
    public List<User> getUsers();
    public int saveNote(Note note);
    public int deleteNote(Note note);
    public int updateNote(Note note);
}
