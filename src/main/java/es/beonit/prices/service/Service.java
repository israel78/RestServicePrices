package es.beonit.prices.service;


import es.beonit.prices.domain.Prices;

import java.util.List;

public interface Service {

    public List<Prices> getNotesByUserId();
    public boolean login(String userName, String pass);
    public String getUserId(String userName);
    public int saveNote(Prices note);
    public int deleteNote(Prices note);
    public Prices getNotesById(int noteId);
    public int updateNote(Prices note);
}
