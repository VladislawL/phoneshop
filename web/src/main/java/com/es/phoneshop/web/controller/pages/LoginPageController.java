package com.es.phoneshop.web.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequestMapping(value = "/login")
public class LoginPageController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Locale locale,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("authentication.error", null, locale));
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", messageSource.getMessage("logged.out.message", null, locale));
        }

        return "login";
    }

}
