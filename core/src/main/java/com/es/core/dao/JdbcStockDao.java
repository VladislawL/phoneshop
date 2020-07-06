package com.es.core.dao;

import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockRowMapper;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

@Component
public class JdbcStockDao implements StockDao {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private StockRowMapper stockRowMapper;

    private static final String GET_PHONE_STOCK = "select stocks.* from stocks where phoneId = :phoneId";

    private static final String DECREASE_PHONE_STOCK_QUERY = "update stocks set stock = stock - %s where phoneId = :phoneId";

    private static final String SELECT_STOCK_BY_PHONE_ID = "select stock from stocks where phoneId = :phoneId";

    @Override
    public Stock getStock(long phoneId) {
        Map<String, Object> phoneIdParameter = Collections.singletonMap("phoneId", phoneId);

        return namedParameterJdbcTemplate.queryForObject(GET_PHONE_STOCK, phoneIdParameter, stockRowMapper);
    }

    @Override
    public void decreaseProductStock(long phoneId, long quantity) throws OutOfStockException {
        Map<String, Object> phoneIdParameter = Collections.singletonMap("phoneId", phoneId);

        if (checkStock(phoneId, quantity)) {
            namedParameterJdbcTemplate.update(String.format(DECREASE_PHONE_STOCK_QUERY, quantity), phoneIdParameter);
        } else {
            throw new OutOfStockException();
        }
    }

    @Override
    public boolean checkStock(long phoneId, long quantity) {
        Map<String, Object> phoneIdParameter = Collections.singletonMap("phoneId", phoneId);

        Long stock = namedParameterJdbcTemplate.queryForObject(SELECT_STOCK_BY_PHONE_ID, phoneIdParameter, Long.class);

        if (stock >= quantity) {
            return true;
        } else {
            return false;
        }
    }

}
