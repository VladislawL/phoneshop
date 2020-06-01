package com.es.core.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcStockDao implements StockDao {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String GET_PHONE_STOCK = "select stock from stocks where phoneId = :phoneId";

    @Override
    public long getStock(long phoneId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("phoneId", phoneId);

        int stock = namedParameterJdbcTemplate.queryForObject(GET_PHONE_STOCK, parameters, Integer.class);

        return stock;
    }
}
