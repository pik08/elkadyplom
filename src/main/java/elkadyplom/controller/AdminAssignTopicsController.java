package elkadyplom.controller;

import elkadyplom.service.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

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
    public ResponseEntity<?> doAssign(Locale locale) {
        declarationService.assignTopics();
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
