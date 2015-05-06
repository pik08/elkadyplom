package com.springapp.mvc.controllers;

import com.springapp.mvc.App;
import com.springapp.mvc.forms.SingInForm;
import com.springapp.mvc.grains.UserSession;
import com.springapp.mvc.model.Model;
import com.springapp.mvc.database.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView home(ModelMap modelMap)
    {
        ModelAndView modelAndView = new ModelAndView(App.PIK_START_PAGE);
        modelAndView.addObject("singInForm", new SingInForm());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String homePost(@ModelAttribute("SpringWeb") SingInForm singInForm)
    {
        String url = "redirect:";
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
                url += "" + App.PIK_CONTROLLER_URL;
        }
        return url;
    }
}
