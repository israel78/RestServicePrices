package es.overon.poc.notes.service;
import java.util.List;
import java.util.stream.Collectors;

import es.overon.poc.notes.domain.Note;
import es.overon.poc.notes.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Dao dao;

    public List<Note> getNotesByUserId(int userId) {
        return dao.getNotes().stream().filter(n -> n.getUser().getId() == userId).collect(Collectors.toList());
    }
    public boolean login(String userName, String pass) {
        return null != dao.getUsers().stream()
                .filter(u -> u.getFirstName().equals(userName))
                .filter(u -> u.getPassw().equals(pass)).findAny().orElse(null);
    }
    public Note getNotesById(int noteId){
        return dao.getNotes().stream().filter(n -> n.getId() == noteId).findFirst().get();
    }
    public int updateNote(Note note) {
        return dao.updateNote(note);
    }
    public int saveNote(Note note){
      return  dao.saveNote(note);
    };
    public int deleteNote(Note note){
        return dao.deleteNote(note);
    }
    public String getUserId(String userName) {
         return String.valueOf(dao.getUsers().stream()
                .filter(u -> u.getFirstName().equals(userName))
                .findAny().orElse(null).getId());

    }

}

