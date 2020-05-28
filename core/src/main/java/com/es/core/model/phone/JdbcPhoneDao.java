package com.es.core.model.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PhoneRowMapper phoneRowMapper;

    private final String INSERT_PHONE_QUERY = "insert into phones (id, brand, model, price, displaySizeInches, weightGr, " +
            "lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, " +
            "backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, " +
            "standByTimeHours, bluetooth, positioning, imageUrl, description) " +
            "values (:id, :brand, :model, :price, :displaySizeInches, :weightGr, " +
            ":lengthMm, :widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, " +
            ":backCameraMegapixels, :frontCameraMegapixels, :ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, " +
            ":standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";

    private final String UPDATE_PHONE_QUERY = "update phones set brand = :brand, model = :model, price = :price, " +
            "displaySizeInches = :displaySizeInches, weightGr = :weightGr, lengthMm = :lengthMm, widthMm = :widthMm, " +
            "heightMm = :heightMm, announced = :announced, deviceType = :deviceType, os = :os, " +
            "displayResolution = :displayResolution, pixelDensity = :pixelDensity, displayTechnology = :displayTechnology, " +
            "backCameraMegapixels = :backCameraMegapixels, frontCameraMegapixels = :frontCameraMegapixels, ramGb = :ramGb, " +
            "internalStorageGb = :internalStorageGb, batteryCapacityMah = :batteryCapacityMah, talkTimeHours = :talkTimeHours, " +
            "standByTimeHours = :standByTimeHours, bluetooth = :bluetooth, positioning = :positioning, " +
            "imageUrl = :imageUrl, description = :description where id = :id";

    private final String DELETE_PHONE_QUERY = "delete from phone2color where phoneId = :id";

    private final String GET_PHONE_QUERY = "select * from phones where phones.id = :id";

    private final String JOIN_PHONE_AND_COLOR_QUERY = "insert into phone2color (phoneId, colorId) values (:phoneId, :colorId)";

    private final String FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT_QUERY = "select * from phones offset :offset limit :limit";

    public Optional<Phone> get(final Long key) {
        try {
            Map<String, Object> idParameter = Collections.singletonMap("id", key);
            return Optional.of(namedParameterJdbcTemplate.queryForObject(GET_PHONE_QUERY, idParameter, phoneRowMapper));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        if (phone.getId() == null) {
            add(phone);
        } else {
            update(phone);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("offset", offset);
        parameters.put("limit", limit);
        return namedParameterJdbcTemplate.query(FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT_QUERY, parameters, phoneRowMapper);
    }

    public List<Phone> findOrderedPhoneListBySearchQuery(int offset, int limit, String searchQuery, SortOrder sortOrder, SortField sortField) {
        return namedParameterJdbcTemplate.query("select * from phones join stocks on id = phoneId where ("  + getBrandAndModelLikeSearchQueryCondition(searchQuery) +
                ") and stock > 0 and price > 0 order by " + sortField.getAttribute() + " " + sortOrder.name() + " offset " + offset + " limit " + limit, phoneRowMapper);
    }

    public int countPhonesWhereBrandAndModelLikeSearchQuery(String searchQuery) {
        return jdbcTemplate.queryForObject("select count(*) from phones join stocks on id = phoneId where (" +
                getBrandAndModelLikeSearchQueryCondition(searchQuery) + ") and stock > 0 and price > 0", Integer.class);
    }

    private void update(final Phone phone) {
        Map<String, Object> idParameter = Collections.singletonMap("id", phone.getId());
        SqlParameterSource phoneParameterSource = new BeanPropertySqlParameterSource(phone);

        namedParameterJdbcTemplate.update(DELETE_PHONE_QUERY, idParameter);
        namedParameterJdbcTemplate.update(UPDATE_PHONE_QUERY, phoneParameterSource);

        setPhoneColors(phone);
    }

    private void add(final Phone phone) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(phone);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_PHONE_QUERY, parameterSource, keyHolder);

        Long id = keyHolder.getKey().longValue();
        phone.setId(id);

        setPhoneColors(phone);
    }

    private String getBrandAndModelLikeSearchQueryCondition(String searchQuery) {
        StringBuilder result = new StringBuilder("lower(brand) like '%" + searchQuery + "%' or lower(model) like '%" + searchQuery + "%'");
        String[] words = searchQuery.split(" ");

        for (String word : words) {
            result.append(" or lower(brand) like '%")
                    .append(word.toCharArray())
                    .append("%' or lower(model) like '%")
                    .append(word.toLowerCase())
                    .append("%'");
        }

        return result.toString();
    }

    private void setPhoneColors(Phone phone) {
        for (Color color : phone.getColors()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("phoneId", phone.getId());
            parameters.put("colorId", color.getId());

            namedParameterJdbcTemplate.update(JOIN_PHONE_AND_COLOR_QUERY, parameters);
        }
    }
}
