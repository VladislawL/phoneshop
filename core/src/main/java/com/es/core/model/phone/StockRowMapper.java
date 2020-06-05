package com.es.core.model.phone;

import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper extends BeanPropertyRowMapper<Stock> {

    @Autowired
    private PhoneService phoneService;

    public StockRowMapper() {
        super(Stock.class);
    }

    @Override
    public Stock mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Stock stock = super.mapRow(rs, rowNumber);
        Phone phone = phoneService.getPhoneById(rs.getLong("phoneId"));
        stock.setPhone(phone);
        return stock;
    }
}
