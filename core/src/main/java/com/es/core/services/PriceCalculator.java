package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;

import java.math.BigDecimal;

public interface PriceCalculator {
    void calculateSubtotalPrice(Cart cart);
    BigDecimal getDeliveryPrice();
    void calculateTotalPrice(Order order);
}
