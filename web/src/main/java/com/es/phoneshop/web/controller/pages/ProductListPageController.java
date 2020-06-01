package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortField", required = false, defaultValue = "PRICE") SortField sortField,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") SortOrder sortOrder,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            Model model) {
        int pagesCount = phoneService.getPagesCount(query);

        model.addAttribute("phones", phoneService.getPhonePage(page, query, sortField, sortOrder));
        model.addAttribute("pagesCount", pagesCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("cart", cartService.getCart());

        return "productList";
    }
}
