package com.es.core.services;

import com.es.core.order.OutOfStockException;

import java.util.Map;

public interface StockService {
    long getStock(long phoneId);
    void decreaseProductStock(Map<Long, Long> phones) throws OutOfStockException;
}
