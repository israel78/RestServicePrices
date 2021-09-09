package es.beonit.prices.service;
import java.util.List;
import java.util.stream.Collectors;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Dao dao;

    public List<Prices> getNotesByUserId() {
        return dao.getNotes();
    }

    public boolean login(String userName, String pass) {
        return null != dao.getUsers().stream()
                .filter(u -> u.getFirstName().equals(userName))
                .filter(u -> u.getPassw().equals(pass)).findAny().orElse(null);
    }
    public Prices getNotesById(int noteId){
        return (Prices) dao.getNotes();
    }
    public int updateNote(Prices note) {
        return dao.updateNote(note);
    }
    public int saveNote(Prices note){
      return  dao.saveNote(note);
    };
    public int deleteNote(Prices note){
        return dao.deleteNote(note);
    }
    public String getUserId(String userName) {
         return String.valueOf(dao.getUsers().stream()
                .filter(u -> u.getFirstName().equals(userName))
                .findAny().orElse(null).getId());

    }

}

