package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import com.es.core.services.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PriceCalculator priceCalculator;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order getOrderByUUID(UUID uuid) throws OrderNotFoundException {
        Optional<Order> order = orderDao.getOrderByUUID(uuid);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException(uuid.toString());
        }
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderDao.getOrderById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException(id.toString());
        }
    }

    @Override
    public Order createOrder(Cart cart) throws OutOfStockException {
        Order order = new Order();

        order.setUuid(UUID.randomUUID());
        order.setOrderItems(getOrderItemsFromCart(cart, order));
        order.setSubtotal(cart.getSubTotalPrice());
        order.setDeliveryPrice(priceCalculator.getDeliveryPrice());
        priceCalculator.calculateTotalPrice(order);
        order.setStatus(OrderStatus.NEW);

        orderDao.save(order);

        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        orderDao.save(order);
    }

    private List<OrderItem> getOrderItemsFromCart(Cart cart, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Phone phone = phoneService.getPhoneById(cartItem.getPhoneId());
            orderItems.add(new OrderItem(phone, order, cartItem.getQuantity()));
        }

        return orderItems;
    }

}
