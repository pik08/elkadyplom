package elkadyplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Kontroler obsługujący zapytania do strony początkowej.
 */

@Controller
@RequestMapping(value = "/")
public class HomeController {

    /**
     * Metoda przekierowująca zapytania na stronę powitalną.
     * @return przekierowanie na stronę powitalną
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    public String redirect(){
        return "redirect:/protected/home";
    }

    /**
     * Metoda blokująca zapytania AJAXowe do tej strony.
     * @return ResponseEntity z komunikatem Forbidden
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, produces = "application/json")
    public ResponseEntity<?> doGetAjax() {
        return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
    }    
}
