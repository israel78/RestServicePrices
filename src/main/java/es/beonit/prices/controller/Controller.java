package es.beonit.prices.controller;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @RequestMapping(value = "/getPriceApply")
    public ResponseEntity<Prices> getPriceResponse(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(name = "DateApply") String dateApply,
            @RequestParam(name = "productId") int productId,
            @RequestParam(name = "brandId") int brandId,
            HttpSession session
    ) throws ParseException {
        Prices priceResponse = new Prices();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        Date date = formatter.parse(dateApply);
        if (token != null && token.equals(session.getAttribute("Token"))) {
            priceResponse = userService.getPriceResponse(date, productId, brandId);
            return new ResponseEntity<Prices>(priceResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<Prices>(priceResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestHeader(name = "pass", required = true) String pass,
                                                     @RequestHeader(name = "user", required = true) String userName,
                                                     HttpSession session) {
        Map<String, String> result = new <String, String>HashMap();
        if (userService.login(userName, pass)) {
            String token = generateNewToken();
            result.put("Token", token);
            result.put("id", userService.getUserId(userName));
            session.setAttribute("Token", token);
            return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
        } else {
            result.put("Token", "Not authorized");
            return new ResponseEntity<Map<String, String>>(result, HttpStatus.UNAUTHORIZED);
        }
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
