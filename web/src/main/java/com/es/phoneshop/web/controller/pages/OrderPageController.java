package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartPageData;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderPageData;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.core.services.AttributeService;
import com.es.core.services.CartPageDataService;
import com.es.core.services.PriceCalculator;
import com.es.core.validators.CartItemsValidator;
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
    private Cart cart;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private CartItemsValidator cartItemsValidator;

    @Autowired
    private CartPageDataService cartPageDataService;

    @Autowired
    private PriceCalculator priceCalculator;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(@ModelAttribute OrderPageData orderPageData, Model model) {
        model.addAttribute("orderPageData", orderPageData);
        addOrderPageAttributes(model);

        return "orderPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated OrderPageData orderPageData,
                             Errors errors, Model model) throws OutOfStockException {
        cartItemsValidator.validate(cart.getCartItems(), errors);
        if (!errors.hasErrors()) {
            Order order = orderService.createOrder(cart);
            setContactInformation(order, orderPageData);
            orderService.placeOrder(order);

            return "redirect:orderOverview/" + order.getUuid();
        } else {
            return getOrder(orderPageData, model);
        }
    }

    private void setContactInformation(Order order, OrderPageData orderPageData) {
        order.setFirstName(orderPageData.getFirstName());
        order.setLastName(orderPageData.getLastName());
        order.setContactPhoneNo(orderPageData.getContactPhoneNo());
        order.setDeliveryAddress(orderPageData.getDeliveryAddress());
    }

    private void addOrderPageAttributes(Model model) {
        CartPageData cartPageData = cartPageDataService.createCartPageData();

        model.addAttribute("deliveryPrice", priceCalculator.getDeliveryPrice());
        model.addAttribute("attributes", attributeService.getAttributes());
        model.addAttribute("cartPageData", cartPageData);
    }

}
