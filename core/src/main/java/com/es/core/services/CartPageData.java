package com.es.core.services;

import java.util.Map;

public class CartPageData {

    private Map<Long, Long> cartItems;

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }
}
