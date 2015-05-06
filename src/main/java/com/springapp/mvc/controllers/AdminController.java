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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by PK on 4/8/2015.
 */
@Controller
@Scope("session")
@RequestMapping(App.ADMIN_CONSTROLLER_URL)
public class AdminController
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
        ModelAndView modelAndView = new ModelAndView(App.ADMIN);
        return modelAndView;
    }
}
