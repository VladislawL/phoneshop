package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.AbstractDataBaseIntegrationTest;
import com.es.core.dao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderServiceImplTest extends AbstractDataBaseIntegrationTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderDao orderDao;

    @Test
    public void shouldCreateOrder() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(1L, 1L);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        cart.setSubTotalPrice(BigDecimal.ONE);

        Order order = orderService.createOrder(cart);

        assertThat(order).matches(o -> o.getSubtotal().equals(BigDecimal.ONE))
                .matches(o -> o.getDeliveryPrice().equals(BigDecimal.valueOf(1)))
                .matches(o -> o.getTotalPrice().equals(BigDecimal.valueOf(2)))
                .matches(o -> o.getStatus().equals(OrderStatus.NEW));
    }

    @Test
    public void shouldPlaceOrder() throws OutOfStockException {
        Order expectedOrder = new Order();

        long quantity = 1L;
        Phone phone = new Phone();
        phone.setId(2L);

        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(phone);
        orderItem.setOrder(expectedOrder);
        orderItem.setQuantity(quantity);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        UUID uuid = UUID.fromString("12345678-1234-1234-3456-000000000000");
        OrderStatus status = OrderStatus.NEW;

        expectedOrder.setId(1L);
        expectedOrder.setSubtotal(BigDecimal.ONE);
        expectedOrder.setDeliveryPrice(BigDecimal.ONE);
        expectedOrder.setTotalPrice(BigDecimal.valueOf(2));
        expectedOrder.setStatus(status);
        expectedOrder.setUuid(uuid);
        expectedOrder.setDeliveryAddress("test address");
        expectedOrder.setFirstName("John");
        expectedOrder.setLastName("Smith");
        expectedOrder.setContactPhoneNo("+375291112233");
        expectedOrder.setOrderItems(orderItems);

        orderService.placeOrder(expectedOrder);

        Order order = orderDao.getOrder(uuid).get();

        assertThat(order).matches(o -> o.getSubtotal().equals(expectedOrder.getSubtotal()))
                .matches(o -> o.getDeliveryPrice().equals(expectedOrder.getDeliveryPrice()))
                .matches(o -> o.getTotalPrice().equals(expectedOrder.getTotalPrice()))
                .matches(o -> o.getStatus().equals(OrderStatus.NEW))
                .matches(o -> o.getFirstName().equals(expectedOrder.getFirstName()))
                .matches(o -> o.getLastName().equals(expectedOrder.getLastName()))
                .matches(o -> o.getContactPhoneNo().equals(expectedOrder.getContactPhoneNo()));
    }

}
