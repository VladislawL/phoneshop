package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

        private final String UPDATE_QUERY = "update phones set brand = ?, model = ?, price = ?, displaySizeInches = ?, " +
            "weightGr = ?, lengthMm = ?, widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, " +
            "os = ?, displayResolution = ?, pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, " +
            "frontCameraMegapixels = ?, ramGb = ?, internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, " +
            "standByTimeHours = ?, bluetooth = ?, positioning = ?, imageUrl = ?, description = ? where id = ?";

    public Optional<Phone> get(final Long key) {
        return Optional.of(jdbcTemplate.queryForObject("select * from phones where phones.id = ?", new PhoneRowMapper(), key));
    }

    public void save(final Phone phone) {
        if (phone.getId() == null) {
            add(phone);
        } else {
            update(phone);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new PhoneRowMapper());
    }

    private void update(final Phone phone) {
        jdbcTemplate.update(UPDATE_QUERY, phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(),
                phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(),
                phone.getPositioning(), phone.getImageUrl(), phone.getDescription(), phone.getId());

        jdbcTemplate.update("delete from phone2color where phoneId = ?", phone.getId());

        for (Color color: phone.getColors()) {
            jdbcTemplate.update("insert into phone2color (phoneId, colorId) values (?, ?)", phone.getId(), color.getId());
        }
    }

    private void add(final Phone phone) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("brand", phone.getBrand());
        parameters.put("model", phone.getModel());
        parameters.put("price", phone.getPrice());
        parameters.put("displaySizeInches", phone.getDisplaySizeInches());
        parameters.put("weightGr", phone.getWeightGr());
        parameters.put("lengthMm", phone.getLengthMm());
        parameters.put("widthMm", phone.getWidthMm());
        parameters.put("heightMm", phone.getHeightMm());
        parameters.put("announced", phone.getAnnounced());
        parameters.put("deviceType", phone.getDeviceType());
        parameters.put("os", phone.getOs());
        parameters.put("displayResolution", phone.getDisplayResolution());
        parameters.put("pixelDensity", phone.getPixelDensity());
        parameters.put("displayTechnology", phone.getDisplayTechnology());
        parameters.put("backCameraMegapixels", phone.getBackCameraMegapixels());
        parameters.put("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        parameters.put("ramGb", phone.getRamGb());
        parameters.put("internalStorageGb", phone.getInternalStorageGb());
        parameters.put("batteryCapacityMah", phone.getBatteryCapacityMah());
        parameters.put("talkTimeHours", phone.getTalkTimeHours());
        parameters.put("standByTimeHours", phone.getStandByTimeHours());
        parameters.put("bluetooth", phone.getBluetooth());
        parameters.put("positioning", phone.getPositioning());
        parameters.put("imageUrl", phone.getImageUrl());
        parameters.put("description", phone.getDescription());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        for (Color color: phone.getColors()) {
            jdbcTemplate.update("insert into phone2color (phoneId, colorId) values (?, ?)", id, color.getId());
        }
    }

    private class PhoneRowMapper extends BeanPropertyRowMapper<Phone> {
        public PhoneRowMapper() {
            super(Phone.class);
        }

        @Override
        public Phone mapRow(ResultSet rs, int rowNumber) throws SQLException {
            Phone phone = super.mapRow(rs, rowNumber);
            List<Color> colors = jdbcTemplate.query("select colors.* from phone2color" +
                    " join colors on colorId = colors.id where phoneId = " + phone.getId(), new BeanPropertyRowMapper(Color.class));
            phone.setColors(new HashSet<>(colors));
            return phone;
        }
    }
}
