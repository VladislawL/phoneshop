package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order getOrderByUUID(UUID uuid);
    Order getOrderById(Long id);
    List<Order> getOrders();
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
