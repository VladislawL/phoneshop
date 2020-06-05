package com.es.core.services;

import com.es.core.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultStockService implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public long getStock(long phoneId) {
        return stockDao.getStock(phoneId).getStock();
    }
}
