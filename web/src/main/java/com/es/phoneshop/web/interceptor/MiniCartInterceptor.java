package com.es.phoneshop.web.interceptor;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MiniCartInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CartService cartService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Cart cart = cartService.getCart();
            MiniCart miniCart = MiniCart.fromCart(cart);
            modelAndView.addObject("miniCart", miniCart);
        }
    }
}
