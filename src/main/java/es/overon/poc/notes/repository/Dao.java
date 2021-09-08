package es.overon.poc.notes.repository;

import es.overon.poc.notes.domain.Note;
import es.overon.poc.notes.domain.User;

import java.util.List;

public interface Dao {
    public List<Note> getNotes();
    public List<User> getUsers();
    public int saveNote(Note note);
    public int deleteNote(Note note);
    public int updateNote(Note note);
}
