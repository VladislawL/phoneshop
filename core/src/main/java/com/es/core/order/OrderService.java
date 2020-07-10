package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderPageData;

import java.util.UUID;

public interface OrderService {
    Order getOrderByUUID(UUID uuid);
    Order getOrderById(Long id);
    Order createOrder(Cart cart);
    void updateOrder(Order order, Cart cart);
    void setContactInformation(Order order, OrderPageData orderPageData);
    void placeOrder(Order order) throws OutOfStockException;
}
