package es.overon.poc.notes.service;

import es.overon.poc.notes.domain.Note;

import java.util.List;

public interface Service {

    public List<Note> getNotesByUserId(int userId);
    public boolean login(String userName, String pass);
    public String getUserId(String userName);
    public int saveNote(Note note);
    public int deleteNote(Note note);
    public Note getNotesById(int noteId);
    public int updateNote(Note note);
}
