package com.es.core.dao;

import com.es.core.model.phone.Stock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JdbcStockDaoIntegrationTest extends AbstractDataBaseIntegrationTest {

    @Autowired
    private StockDao stockDao;

    @Test
    public void shouldGetStockByPhoneId() {
        long phoneId = 1L;
        long expectedStock = 5;

        Stock stock = stockDao.getStock(phoneId);

        assertThat(stock)
                .matches(s -> s.getPhone().getId().equals(phoneId))
                .matches(s -> s.getStock() == expectedStock);
    }
}
