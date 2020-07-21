package com.es.core.dao;

import com.es.core.model.phone.Stock;
import com.es.core.order.OutOfStockException;

public interface StockDao {
    Stock getStock(long phoneId);
    void decreaseProductStock(long phoneId, long quantity) throws OutOfStockException;
    boolean checkStock(long phoneId, long quantity);
}
