package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class PhoneRowMapper extends BeanPropertyRowMapper<Phone> {
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PhoneRowMapper() {
        super(Phone.class);
    }

    @Override
    public Phone mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Phone phone = super.mapRow(rs, rowNumber);
        List<Color> colors = namedParameterJdbcTemplate.query("select colors.* from phone2color" +
                " join colors on colorId = colors.id where phoneId = " + phone.getId(), new BeanPropertyRowMapper(Color.class));
        phone.setColors(new HashSet<>(colors));
        return phone;
    }
}
