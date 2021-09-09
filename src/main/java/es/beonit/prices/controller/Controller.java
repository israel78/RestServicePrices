package es.beonit.prices.controller;

import java.security.SecureRandom;
import java.util.*;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@Scope("application")
public class Controller {

    @Autowired
    private Service userService;

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    @RequestMapping  (value = "/noteslist")
    public  ResponseEntity<List<Prices>> getNotesByUserId(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(value = "userId") int userId,
            HttpSession session
    ) {
        List<Prices> noteList = new ArrayList<Prices>();
        if(token!=null&&token.equals(session.getAttribute("Token"))) {
            noteList = userService.getNotesByUserId();
            return new ResponseEntity<List<Prices>> (noteList, HttpStatus.OK);
        }else{
           return new ResponseEntity<List<Prices>> (noteList, HttpStatus.UNAUTHORIZED);
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
           return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
       } else {
            result.put("Token", "Not authorized");
           return new ResponseEntity<Map<String, String>>(result, HttpStatus.UNAUTHORIZED);
        }
       }
    @RequestMapping  (value = "/save")
    public  ResponseEntity<Map<String, String>> saveOrUpdateNote(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody Prices note,
            HttpSession session
    ) {
        Map<String, String> result = new <String, String>HashMap();
        if(token!=null&&token.equals(session.getAttribute("Token"))){
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
            @RequestBody Prices note,
            HttpSession session
    ) {
        Map<String, String> result = new <String, String>HashMap();
        if(token!=null&&token.equals(session.getAttribute("Token"))){
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
        if(token!=null&&token.equals(session.getAttribute("Token"))) {
            Prices note =  userService.getNotesById(noteId);
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
