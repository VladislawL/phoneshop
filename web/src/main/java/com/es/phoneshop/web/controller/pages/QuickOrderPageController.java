package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.order.QuickOrder;
import com.es.core.services.QuickOrderService;
import com.es.core.validators.QuickOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/quickOrder")
public class QuickOrderPageController {

    @Autowired
    private QuickOrderService quickOrderService;

    @Autowired
    private QuickOrderValidator quickOrderValidator;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getQuickOrderPage(Model model) {
        model.addAttribute("quickOrder", quickOrderService.getEmptyQuickOrder());

        return "quickOrder";
    }

    @PostMapping
    public String addAllCartItemsToCart(@ModelAttribute @Valid QuickOrder quickOrder, Errors errors) {
        quickOrderValidator.validate(quickOrder, errors);

        cartService.addCartItems(quickOrder.getCartItems(), errors);

        return "quickOrder";
    }

}
