package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper extends BeanPropertyRowMapper<OrderItem> {

    private PhoneService phoneService;

    public OrderItemRowMapper() {
        super(OrderItem.class);
    }

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNumber) throws SQLException {
        OrderItem orderItem = super.mapRow(rs, rowNumber);

        Phone phone = phoneService.getPhoneById(rs.getLong("phoneId"));
        orderItem.setPhone(phone);

        return orderItem;
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }
}
