package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addPhone(@RequestBody @Valid CartItem cartItem, Errors errors) {
        if (errors.hasErrors()) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", errors.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            Cart cart = cartService.getCart();
            Map<String, Object> miniCart = new HashMap<>();
            miniCart.put("subTotalPrice", cart.getSubTotalPrice().toString());
            miniCart.put("itemsNumber", cart.getCartItems().size());
            return ResponseEntity.ok(miniCart);
        }
    }
}