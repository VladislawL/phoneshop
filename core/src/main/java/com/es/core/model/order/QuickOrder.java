package com.es.core.model.order;

import com.es.core.cart.CartItem;

import java.util.ArrayList;

public class QuickOrder {
    private ArrayList<CartItem> cartItems;

    public QuickOrder() {
        cartItems = new ArrayList<>();
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
