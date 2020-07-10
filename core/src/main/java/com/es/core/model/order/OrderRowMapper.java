package com.es.core.model.order;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class OrderRowMapper extends BeanPropertyRowMapper<Order> {

    public OrderRowMapper() {
        super(Order.class);
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Order order = super.mapRow(rs, rowNumber);

        int statusId = rs.getInt("status");
        OrderStatus orderStatus = OrderStatus.values()[statusId];

        order.setStatus(orderStatus);

        return order;
    }

}
