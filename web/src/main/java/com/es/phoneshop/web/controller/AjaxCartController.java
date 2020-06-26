package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.utils.PriceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Autowired
    private PriceFormatter priceFormatter;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPhone(@RequestBody @Valid CartItem cartItem, Errors errors) {
        if (!errors.hasErrors()) {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            Cart cart = cartService.getCart();

            MiniCart miniCart = MiniCart.fromCart(cart);

            Map<String, Object> response = new HashMap<>();
            response.put("miniCart", miniCart);
            response.put("currency", priceFormatter.getDefaultCurrency());
            return response;
        } else {
            throw new CartItemValidationException(errors.getAllErrors());
        }
    }
}