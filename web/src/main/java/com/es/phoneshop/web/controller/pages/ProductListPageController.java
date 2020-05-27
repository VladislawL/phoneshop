package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @Autowired
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortField", required = false, defaultValue = "PRICE") SortField sortField,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") SortOrder sortOrder,
            Model model) {

        model.addAttribute("phones", phoneService.getPhonePage(1, sortField, sortOrder));
        return "productList";
    }
}
