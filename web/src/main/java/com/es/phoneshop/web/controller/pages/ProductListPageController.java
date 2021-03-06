package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.SortOrder;
import com.es.core.services.AttributeService;
import com.es.core.services.PhoneService;
import com.es.core.utils.PriceFormatter;
import com.es.phoneshop.web.pagedata.PaginationData;
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
    private PaginationData paginationData;

    @Autowired
    private AttributeService attributeService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortField", required = false, defaultValue = "price") String sortField,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") SortOrder sortOrder,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            Model model) {
        int pagesCount = phoneService.getPagesCount(query);

        paginationData.setCurrentPage(page);
        paginationData.setPagesCount(pagesCount);
        paginationData.setPhones(phoneService.getPhonePage(page, query, sortField, sortOrder));

        model.addAttribute("paginationData", paginationData);
        model.addAttribute("attributes", attributeService.getAttributes());

        return "productList";
    }
}
