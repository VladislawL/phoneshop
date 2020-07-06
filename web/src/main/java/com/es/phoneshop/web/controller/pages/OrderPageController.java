package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartPageData;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.core.services.AttributeService;
import com.es.core.services.CartPageDataService;
import com.es.core.validators.CartPageDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Resource
    private OrderService orderService;

    @Autowired
    private CartPageDataService cartPageDataService;

    @Autowired
    private CartService cartService;

    @Autowired
    private Cart cart;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private CartPageDataValidator cartPageDataValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(@ModelAttribute CartPageData cartPageData, Errors errors, Model model) throws OutOfStockException {
        cartPageDataValidator.validate(cartPageData, errors);
        model.addAttribute("attributes", attributeService.getAttributes());
        if (!errors.hasErrors() && cartPageData.getCartItems().size() != 0) {
            cartService.updatePhone(cartPageDataService.convertCartItems(cartPageData));
            Order order = orderService.createOrder(cart);

            model.addAttribute("order", order);

            return "orderPage";
        } else {
            cartPageData.setSubTotalPrice(cart.getSubTotalPrice());
            model.addAttribute("cartPageData", cartPageData);

            return "cartPage";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated @ModelAttribute Order order, Errors errors, Model model) throws OutOfStockException {
        if (!errors.hasErrors()) {
            orderService.placeOrder(order);
            return "redirect:orderOverview/" + order.getUuid();
        } else {
            model.addAttribute("order", order);
            model.addAttribute("attributes", attributeService.getAttributes());

            return "orderPage";
        }
    }

}
