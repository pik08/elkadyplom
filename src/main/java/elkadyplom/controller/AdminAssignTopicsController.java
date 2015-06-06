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

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping(value = "/protected/assign")
public class AdminAssignTopicsController {

    @Autowired
    DeclarationService declarationService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("assign");
    }

    @RequestMapping(value = "/doAssign", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> doAssignGet(Locale locale) {

        List<AssignmentDto> result = declarationService.assignTopics();
        if (result == null)
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<List<AssignmentDto>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/doAssign", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> doAssign(@RequestBody AssignmentDto[] assignments, Locale locale) {

        if (!declarationService.saveAssignedTopics(Arrays.asList(assignments)))
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
