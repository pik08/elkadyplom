package com.springapp.mvc.controllers;

import com.springapp.mvc.App;
import com.springapp.mvc.forms.SingInForm;
import com.springapp.mvc.grains.UserSession;
import com.springapp.mvc.model.Model;
import com.springapp.mvc.database.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.Authenticator;

/**
 * Created by PK on 4/3/2015.
 */
@Controller
@Scope("session")
@RequestMapping(App.PIK_CONTROLLER_URL)
public class PikController
{
    @Autowired
    private Model model;
    @Autowired
    private DataSource database;
    @Autowired
    private UserSession userSession;

    @RequestMapping(method = RequestMethod.GET)
    public String homePost()
    {
        SingInForm singInForm = new SingInForm();
        String url = "redirect:";
        ModelAndView modelAndView = new ModelAndView(App.PIK_START_PAGE);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if((authentication instanceof AnonymousAuthenticationToken)) {
            return url += "" + App.PIK_CONTROLLER_URL;
        }
        userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO
        if(userDetails.getUsername().equals("admin"))
            singInForm.setWho(SingInForm.Who.Admin);
        else if(userDetails.getUsername().equals("promoter"))
            singInForm.setWho(SingInForm.Who.Promoter);
        else if (userDetails.getUsername().equals("student"))
            singInForm.setWho(SingInForm.Who.Student);
        //END TODO
        switch (singInForm.getWho())
        {
            case Promoter:
                userSession.setRoleTmp("Promoter");
                url += "" + App.PROMOTER_CONTROLLER_URL;
                break;
            case Admin:
                userSession.setRoleTmp("Admin");
                url += "" + App.ADMIN_CONSTROLLER_URL;
                break;
            case Student:
                userSession.setRoleTmp("Student");
                url += "" + App.STUDENT_CONTROLLER_URL;
                break;
            default:
                userSession.setRoleTmp("Anonymous");
                url = "" + App.LOGIN_URL;
        }
        return url;
    }
}
