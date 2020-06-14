package com.es.core.validators;

import com.es.core.services.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext-core.xml"})
public class StockValidatorTest {

    @Autowired
    private StockService stockService;

    @Test
    public void shouldReturnTrueIfQuantityIsLessThanStock() {
        QuantityValidator stockValidator = new QuantityValidator();
        long phoneId = 1;
        long quantity = 1;

        stockValidator.setStockService(stockService);
        assertThat(stockValidator.isValid(phoneId, quantity)).isTrue();
    }

    @Test
    public void shouldReturnFalseIfQuantityIsGreaterThanStock() {
        QuantityValidator stockValidator = new QuantityValidator();
        long phoneId = 1;
        long quantity = 10;

        stockValidator.setStockService(stockService);
        assertThat(stockValidator.isValid(phoneId, quantity)).isFalse();
    }

}
