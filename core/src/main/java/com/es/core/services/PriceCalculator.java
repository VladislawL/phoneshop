package com.es.core.services;

import com.es.core.cart.Cart;

import java.math.BigDecimal;

public interface PriceCalculator {
    void calculateSubtotalPrice(Cart cart);
}
