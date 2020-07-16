package com.es.core.services;

import com.es.core.order.OutOfStockException;

public interface StockService {
    long getStock(long phoneId);
    void decreaseProductStock(long phoneId, long quantity) throws OutOfStockException;
}
