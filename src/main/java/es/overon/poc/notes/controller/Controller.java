package es.overon.poc.notes.controller;

import java.security.SecureRandom;
import java.util.*;

import es.overon.poc.notes.domain.Note;
import es.overon.poc.notes.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@Scope("application")
public class Controller {

    private String token;
    @Autowired
    private Service userService;

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    @RequestMapping  (value = "/noteslist")
    public  ResponseEntity<List<Note>> getNotesByUserId(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(value = "userId") int userId,
            HttpSession session
    ) {
        List<Note> noteList = new ArrayList<Note>();
        if(this.token!=null&&this.token.equals(token)) {
            noteList = userService.getNotesByUserId(userId);
            return new ResponseEntity<List<Note>> (noteList, HttpStatus.OK);
        }else{
           return new ResponseEntity<List<Note>> (noteList, HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping  (value = "/login", produces = "application/json")
    public  ResponseEntity<Map<String, String>> login(@RequestHeader(name = "pass", required = true) String pass,
                                          @RequestHeader(name = "user", required = true) String userName,
                                           HttpSession session) {
        Map<String, String> result = new <String, String>HashMap();
        if(userService.login(userName,pass)) {
            String token = generateNewToken();
            result.put("Token", token);
            result.put("id",userService.getUserId(userName));
            session.setAttribute("Token",token);
            this.token = token;
           return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
       } else {
            result.put("Token", "Not authorized");
           return new ResponseEntity<Map<String, String>>(result, HttpStatus.UNAUTHORIZED);
        }
       }
    @RequestMapping  (value = "/save")
    public  ResponseEntity<Map<String, String>> saveOrUpdateNote(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody Note note,
            HttpSession session
    ) {
        Map<String, String> result = new <String, String>HashMap();
        if(this.token!=null&&this.token.equals(token)){
            result.put("noteId",String.valueOf(userService.saveNote(note)));
            return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
        }else{
            result.put("Token", "Not authorized");
            return new ResponseEntity<Map<String, String>> (result, HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping  (value = "/update")
    public  ResponseEntity<Map<String, String>> updateNote(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody Note note,
            HttpSession session
    ) {
        Map<String, String> result = new <String, String>HashMap();
        if(this.token!=null&&this.token.equals(token)){
            result.put("noteId",String.valueOf(userService.updateNote(note)));
            return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
        }else{
            result.put("Token", "Not authorized");
            return new ResponseEntity<Map<String, String>> (result, HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping  (value = "/delete")
    public  ResponseEntity<Map<String, String>> deleteNote(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam int noteId,
            HttpSession session
    ) {
        Map<String, String> result = new <String, Integer>HashMap();
        if(this.token!=null&&this.token.equals(token)) {
            Note note =  userService.getNotesById(noteId);
            result.put("noteId",String.valueOf(userService.deleteNote(note)));
            return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
        }else{
            result.put("Token", "Not authorized");
            return new ResponseEntity<Map<String, String>> (result, HttpStatus.UNAUTHORIZED);
        }
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return "Bearer "+base64Encoder.encodeToString(randomBytes);
    }
}
