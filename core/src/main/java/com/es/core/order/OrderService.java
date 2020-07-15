package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;

import java.util.UUID;

public interface OrderService {
    Order getOrderByUUID(UUID uuid);
    Order getOrderById(Long id);
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
}
