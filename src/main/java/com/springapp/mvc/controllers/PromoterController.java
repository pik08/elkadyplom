package com.springapp.mvc.controllers;

import com.springapp.mvc.App;
import com.springapp.mvc.database.DataSource;
import com.springapp.mvc.forms.SingInForm;
import com.springapp.mvc.grains.UserSession;
import com.springapp.mvc.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by PK on 4/8/2015.
 */
@Controller
@Scope("session")
@RequestMapping(App.PROMOTER_CONTROLLER_URL)
public class PromoterController
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
        ModelAndView modelAndView = new ModelAndView(App.PROMOTER);
        return modelAndView;
    }

    @RequestMapping(value = App.HOME, method = RequestMethod.POST)
    public String homePost(@ModelAttribute("SpringWeb") SingInForm singInForm)
    {   //TODO
        ModelAndView modelAndView = new ModelAndView();
        switch (singInForm.getWho())
        {
            case Promoter:
                return "redirect:" + App.PROMOTER_CONTROLLER_URL;
            case Admin:
                return "redirect:" + App.ADMIN_CONSTROLLER_URL;
            case Student:
                return "redirect:" + App.STUDENT_CONTROLLER_URL;
            default:
                return "redirect:" + App.BASKETS;
        }
    }

    @RequestMapping(value = App.PROMOTER_CONTROLLER_URL, method = RequestMethod.GET)
    public ModelAndView promotorPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(App.PROMOTER_MAIN_VIEW);
        modelAndView.addObject("baskets", database.getBaskets(1));
        return  modelAndView;
    }


}
