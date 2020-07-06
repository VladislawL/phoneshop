package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderItemRowMapper;
import com.es.core.model.order.OrderRowMapper;
import com.es.core.order.OutOfStockException;
import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcOrderDao implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private OrderRowMapper orderRowMapper;

    @Autowired
    private OrderItemRowMapper orderItemRowMapper;

    @Autowired
    private StockService stockService;

    private static final String INSERT_ORDER_QUERY = "insert into orders (uuid, subtotal, deliveryPrice, totalPrice, status) " +
            "values (:uuid, :subtotal, :deliveryPrice, :totalPrice, :status)";

    private static final String INSERT_ORDER_ITEM_QUERY = "insert into orderItems (orderId, phoneId, quantity) values (" +
            ":orderId, :phoneId, :quantity)";

    private static final String SELECT_ORDER_BY_UUID_QUERY = "select * from orders where uuid = :uuid";

    private static final String SELECT_ORDER_BY_ID_QUERY = "select * from orders where id = :id";

    private static final String SELECT_ORDER_ITEM_BY_UUID_QUERY = "select * from orderItems where orderId = (" +
            "select id from orders where uuid = :uuid)";

    private static final String SELECT_ORDER_ITEM_BY_ORDER_ID_QUERY = "select * from orderItems where orderId = :id";

    private static final String UPDATE_ORDER_QUERY = "update orders set firstName = :firstName, lastName = :lastName, " +
            "deliveryAddress = :deliveryAddress, contactPhoneNo = :contactPhoneNo, subtotal = :subtotal, " +
            "deliveryPrice = :deliveryPrice, totalPrice = :totalPrice, status = :status where uuid = :uuid";

    private static final String DELETE_ORDER_ITEM_BY_UUID_QUERY = "delete from orderItems where orderId = (select id " +
            "from orders where uuid = :uuid)";

    @Override
    public Optional<Order> getOrderByUUID(UUID uuid) {
        try {
            Map<String, Object> uuidParameter = Collections.singletonMap("uuid", uuid);
            return getOrder(uuidParameter, SELECT_ORDER_BY_UUID_QUERY, SELECT_ORDER_ITEM_BY_UUID_QUERY);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        try {
            Map<String, Object> idParameter = Collections.singletonMap("id", id);
            return getOrder(idParameter, SELECT_ORDER_BY_ID_QUERY, SELECT_ORDER_ITEM_BY_ORDER_ID_QUERY);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private Optional<Order> getOrder(Map<String, Object> parameter, String selectOrderQuery, String selectOrderItemQuery) {
        Order order = namedParameterJdbcTemplate.queryForObject(selectOrderQuery, parameter, orderRowMapper);

        List<OrderItem> orderItems = namedParameterJdbcTemplate.query(selectOrderItemQuery, parameter, orderItemRowMapper);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.setOrderItems(orderItems);

        return Optional.of(order);
    }

    @Override
    public void save(Order order) throws OutOfStockException {
        checkOrderItems(order.getOrderItems());
        if (order.getId() == null) {
            insert(order);
        } else {
            update(order);
        }
    }

    private void insert(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_ORDER_QUERY, getOrderParameters(order), keyHolder);
        order.setId(keyHolder.getKey().longValue());

        insertOrderItems(order);
    }

    private void update(Order order) throws OutOfStockException {
        namedParameterJdbcTemplate.update(UPDATE_ORDER_QUERY, getOrderParameters(order));
        Map<String, Object> uuidParameter = Collections.singletonMap("uuid", order.getUuid());
        List<OrderItem> orderItems = order.getOrderItems();

        for (int i = 0; i < orderItems.size(); i++) {
            namedParameterJdbcTemplate.update(DELETE_ORDER_ITEM_BY_UUID_QUERY, uuidParameter);
        }

        insertOrderItems(order);

        for (OrderItem orderItem : orderItems) {
            stockService.decreaseProductStock(orderItem.getPhone().getId(), orderItem.getQuantity());
        }
    }

    private void insertOrderItems(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
            namedParameterJdbcTemplate.update(INSERT_ORDER_ITEM_QUERY, getOrderItemParameters(orderItem));
        }
    }

    private MapSqlParameterSource getOrderParameters(Order order) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("uuid", order.getUuid());
        parameters.addValue("firstName", order.getFirstName());
        parameters.addValue("lastName", order.getLastName());
        parameters.addValue("deliveryAddress", order.getDeliveryAddress());
        parameters.addValue("contactPhoneNo", order.getContactPhoneNo());
        parameters.addValue("subtotal", order.getSubtotal());
        parameters.addValue("deliveryPrice", order.getDeliveryPrice());
        parameters.addValue("totalPrice", order.getTotalPrice());
        parameters.addValue("status", order.getStatus().getId());
        return parameters;
    }

    private MapSqlParameterSource getOrderItemParameters(OrderItem orderItem) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("phoneId", orderItem.getPhone().getId());
        parameters.addValue("orderId", orderItem.getOrder().getId());
        parameters.addValue("quantity", orderItem.getQuantity());
        return parameters;
    }

    private void checkOrderItems(List<OrderItem> orderItems) throws OutOfStockException {
        for (OrderItem orderItem : orderItems) {
            Long stock = stockService.getStock(orderItem.getPhone().getId());

            if (stock < orderItem.getQuantity()) {
                throw new OutOfStockException();
            }
        }
    }

}