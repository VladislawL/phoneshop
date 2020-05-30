package com.es.core.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcStockDaoIntegrationTest extends AbstractDataBaseIntegrationTest {

    @Autowired
    private StockDao stockDao;

    @Test
    public void shouldGetStockByPhoneId() {

    }
}
