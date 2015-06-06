package elkadyplom.controller;

import elkadyplom.dto.AssignmentDto;
import elkadyplom.service.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Kontroler obsługujący stronę przypisywania tematów do studentów na podstawie ich deklaracji.
 */

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping(value = "/protected/assign")
public class AdminAssignTopicsController {

    @Autowired
    DeclarationService declarationService;

    /**
     * Zwraca widok strony przypisywania tematów.
     * @return widok strony
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("assign");
    }

    /**
     * Metoda pobierająca listę przyporządkowań tematów.
     * @param locale informacja o języku
     * @return ResponseEntity z listą przyporzadkowań tematów albo informacją o błędzie
     */
    @RequestMapping(value = "/doAssign", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> doAssignGet(Locale locale) {

        List<AssignmentDto> result = declarationService.assignTopics();
        if (result == null)
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<List<AssignmentDto>>(result, HttpStatus.OK);
    }

    /**
     * Zapisuje listę przyporządkowań tematów do studentów.
     * @param assignments tablica przyporządkowań (lista tu nie działa)
     * @param locale informacja o języku
     * @return ResponseEntity z listą przyporzadkowań tematów albo informacją o błędzie
     */
    @RequestMapping(value = "/doAssign", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> doAssign(@RequestBody AssignmentDto[] assignments, Locale locale) {

        if (!declarationService.saveAssignedTopics(Arrays.asList(assignments)))
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
