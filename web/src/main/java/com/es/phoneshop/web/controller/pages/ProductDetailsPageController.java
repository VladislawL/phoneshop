package com.es.phoneshop.web.controller.pages;

import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Autowired
    private PhoneService phoneService;

    @RequestMapping(value = "/{id}")
    public String productDetailsPage(
            @PathVariable(name = "id") long id,
            Model model) {
        model.addAttribute("phone", phoneService.getPhoneById(id));
        return "phonePage";
    }

}
