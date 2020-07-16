package com.es.core.services;

import com.es.core.dao.StockDao;
import com.es.core.order.OutOfStockException;
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

    @Override
    public void decreaseProductStock(long phoneId, long quantity) throws OutOfStockException {
        stockDao.decreaseProductStock(phoneId, quantity);
    }
}
