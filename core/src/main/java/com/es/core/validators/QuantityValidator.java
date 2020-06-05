package com.es.core.validators;

import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityValidator {

    @Autowired
    private StockService stockService;

    public boolean isValid(long phoneId, long quantity) {
        long stock = stockService.getStock(phoneId);

        return quantity <= stock;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }
}
