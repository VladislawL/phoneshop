package com.es.core.dao;

import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcStockDao implements StockDao {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private StockRowMapper stockRowMapper;

    private final String GET_PHONE_STOCK = "select stocks.* from stocks where phoneId = :phoneId";

    @Override
    public Stock getStock(long phoneId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("phoneId", phoneId);

        Stock stock = namedParameterJdbcTemplate.queryForObject(GET_PHONE_STOCK, parameters, stockRowMapper);

        return stock;
    }
}
