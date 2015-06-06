package elkadyplom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Kontroler obsługujący stronę logowania.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * Metoda zwracająca widok logowania.
     * @return widok logowania
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    public ModelAndView doGet() {
        return new ModelAndView("login");
    }
}
