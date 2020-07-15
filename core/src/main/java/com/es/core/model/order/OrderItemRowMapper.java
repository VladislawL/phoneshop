package com.es.core.model.order;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper extends BeanPropertyRowMapper<OrderItem> {

    private PhoneDao phoneDao;

    public OrderItemRowMapper() {
        super(OrderItem.class);
    }

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNumber) throws SQLException {
        OrderItem orderItem = super.mapRow(rs, rowNumber);

        Phone phone = phoneDao.get(rs.getLong("phoneId")).get();
        orderItem.setPhone(phone);

        return orderItem;
    }

    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

}
