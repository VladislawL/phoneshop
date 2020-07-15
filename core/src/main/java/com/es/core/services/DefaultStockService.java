package com.es.core.services;

import com.es.core.dao.StockDao;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DefaultStockService implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public long getStock(long phoneId) {
        return stockDao.getStock(phoneId).getStock();
    }

    @Override
    public void decreaseProductStock(Map<Long, Long> phones) throws OutOfStockException {
        for (Map.Entry<Long, Long> phone : phones.entrySet()) {
            stockDao.decreaseProductStock(phone.getKey(), phone.getValue());
        }
    }
}
