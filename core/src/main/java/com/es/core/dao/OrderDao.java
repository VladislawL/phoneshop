package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.order.OutOfStockException;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    Optional<Order> getOrderByUUID(UUID uuid);
    Optional<Order> getOrderById(Long id);
    void save(Order order);
}
