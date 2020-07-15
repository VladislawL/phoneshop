package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JdbcOrderDaoTest extends AbstractDataBaseIntegrationTest {

    @Autowired
    private OrderDao jdbcOrderDao;

    @Test
    public void shouldGetOrderByUUID() {
        UUID uuid = UUID.fromString("12345678-1234-1234-3456-000000000000");
        Optional<Order> order = jdbcOrderDao.getOrderByUUID(uuid);

        assertThat(order).isPresent()
                .get()
                .matches(o -> o.getStatus().equals(OrderStatus.NEW));
        assertThat(order.get().getOrderItems()).isNotNull()
                .asList().hasSize(1)
                .anyMatch(o -> ((OrderItem) o).getPhone().getId().equals(1L));
    }

    @Test
    public void shouldReturnEmptyOptional() {
        UUID uuid = UUID.randomUUID();
        Optional<Order> order = jdbcOrderDao.getOrderByUUID(uuid);

        assertThat(order).isEmpty();
    }

    @Test
    public void shouldSaveOrder() throws OutOfStockException {
        long quantity = 1L;
        Order expectedOrder = new Order();
        Phone phone = new Phone();
        phone.setId(1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(phone);
        orderItem.setOrder(expectedOrder);
        orderItem.setQuantity(quantity);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        UUID uuid = UUID.randomUUID();
        OrderStatus status = OrderStatus.NEW;

        expectedOrder.setStatus(status);
        expectedOrder.setUuid(uuid);
        expectedOrder.setOrderItems(orderItems);

        jdbcOrderDao.save(expectedOrder);

        Optional<Order> order = jdbcOrderDao.getOrderByUUID(uuid);

        assertThat(order).isPresent()
                .get()
                .matches(o -> o.getStatus().equals(status));
        assertThat(order.get().getOrderItems()).isNotNull()
                .asList().hasSize(1)
                .anyMatch(o -> ((OrderItem) o).getPhone().equals(orderItem.getPhone()));
    }

    @Test
    public void shouldUpdateOrder() throws OutOfStockException {
        long quantity = 5L;
        long phoneId = 1L;
        Order expectedOrder = new Order();
        Phone phone = new Phone();
        phone.setId(phoneId);

        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(phone);
        orderItem.setOrder(expectedOrder);
        orderItem.setQuantity(quantity);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        UUID uuid = UUID.fromString("12345678-1234-1234-3456-000000000000");
        OrderStatus status = OrderStatus.NEW;

        expectedOrder.setId(1L);
        expectedOrder.setStatus(status);
        expectedOrder.setUuid(uuid);
        expectedOrder.setOrderItems(orderItems);

        jdbcOrderDao.save(expectedOrder);

        Optional<Order> order = jdbcOrderDao.getOrderByUUID(uuid);

        assertThat(order).isPresent()
                .get()
                .matches(o -> o.getStatus().equals(status));
        assertThat(order.get().getOrderItems()).isNotNull()
                .asList().hasSize(1)
                .anyMatch(o -> ((OrderItem) o).getPhone().equals(orderItem.getPhone()));
    }

}
