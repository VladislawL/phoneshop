package com.es.core.services;

import com.es.core.cart.Cart;

import java.math.BigDecimal;

public interface PriceCalculator {
    BigDecimal calculateSubtotalPrice(Cart cart);
}
