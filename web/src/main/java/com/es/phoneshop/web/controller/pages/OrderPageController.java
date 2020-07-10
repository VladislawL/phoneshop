package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartPageData;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderPageData;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.core.services.AttributeService;
import com.es.core.services.CartPageDataService;
import com.es.core.validators.CartPageDataValidator;
import com.es.core.validators.OrderItemsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

    @Autowired
    private OrderItemsValidator orderItemsValidator;

    private static final String ORDER_SESSION_ATTRIBUTE_NAME = "order";

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(@ModelAttribute CartPageData cartPageData, Errors errors, HttpSession httpSession, Model model) {
        cartPageDataValidator.validate(cartPageData, errors);
        model.addAttribute("attributes", attributeService.getAttributes());
        if (!errors.hasErrors() && cartPageData.getCartItems().size() != 0) {
            cartService.updatePhone(cartPageDataService.convertCartItems(cartPageData));
            Order order;

            if (httpSession.getAttribute(ORDER_SESSION_ATTRIBUTE_NAME) == null) {
                order = orderService.createOrder(cart);
                httpSession.setAttribute("order", order);
            } else {
                order = (Order) httpSession.getAttribute(ORDER_SESSION_ATTRIBUTE_NAME);
                orderService.updateOrder(order, cart);
            }

            model.addAttribute("orderPageData", new OrderPageData());

            return "orderPage";
        } else {
            cartPageData.setSubTotalPrice(cart.getSubTotalPrice());
            model.addAttribute("cartPageData", cartPageData);

            return "cartPage";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated OrderPageData orderPageData,
                             Errors errors, HttpSession httpSession, Model model) throws OutOfStockException {
        Order order = (Order) httpSession.getAttribute(ORDER_SESSION_ATTRIBUTE_NAME);
        orderItemsValidator.validate(order.getOrderItems(), errors);
        if (!errors.hasErrors()) {
            orderService.setContactInformation(order, orderPageData);
            orderService.placeOrder(order);

            httpSession.removeAttribute(ORDER_SESSION_ATTRIBUTE_NAME);

            return "redirect:orderOverview/" + order.getUuid();
        } else {
            model.addAttribute("orderPageData", orderPageData);
            model.addAttribute("attributes", attributeService.getAttributes());

            return "orderPage";
        }
    }

}
