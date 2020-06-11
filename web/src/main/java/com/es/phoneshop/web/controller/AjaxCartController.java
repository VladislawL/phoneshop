package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.utils.PriceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Autowired
    private MiniCart miniCart;

    @Autowired
    private PriceFormatter priceFormatter;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public MiniCart addPhone(@RequestBody @Valid CartItem cartItem, Errors errors) {
        if (!errors.hasErrors()) {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            Cart cart = cartService.getCart();

            miniCart.setSubTotalPrice(priceFormatter.format(cart.getSubTotalPrice(), LocaleContextHolder.getLocale()));
            miniCart.setItemsNumber(cart.getCartItems().size());
            return new MiniCart(miniCart.getSubTotalPrice(), miniCart.getItemsNumber());
        } else {
            throw new CartItemValidationException(errors.getAllErrors());
        }
    }
}