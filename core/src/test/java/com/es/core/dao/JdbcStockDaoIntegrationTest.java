package com.es.core.dao;

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

        long stock = stockDao.getStock(phoneId);

        assertThat(expectedStock).isEqualTo(stock);
    }
}
