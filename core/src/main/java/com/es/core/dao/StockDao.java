package com.es.core.dao;

import com.es.core.model.phone.Stock;

public interface StockDao {
    Stock getStock(long phoneId);
}
