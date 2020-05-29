package com.es.core.dao;

import com.es.core.model.phone.Stock;

public interface StockDao {
    int getStock(long phoneId);
}
