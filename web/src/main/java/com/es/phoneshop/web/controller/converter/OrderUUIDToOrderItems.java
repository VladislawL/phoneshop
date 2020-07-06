package com.es.phoneshop.web.controller.converter;

import com.es.core.model.order.OrderItem;
import com.es.core.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.UUID;

public class OrderUUIDToOrderItems implements Converter<String, List<OrderItem>> {

    @Autowired
    private OrderService orderService;

    @Override
    public List<OrderItem> convert(String id) {
        return orderService.getOrderByUUID(UUID.fromString(id)).getOrderItems();
    }
}
