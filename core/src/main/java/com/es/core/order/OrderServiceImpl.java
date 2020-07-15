package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.OrderDao;
import com.es.core.dao.StockDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import com.es.core.services.PriceCalculator;
import com.es.core.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StockService stockService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private PriceCalculator priceCalculator;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order getOrderByUUID(UUID uuid) throws OrderNotFoundException {
        Optional<Order> order = orderDao.getOrderByUUID(uuid);
        return order.orElseThrow(() -> new OrderNotFoundException(uuid.toString()));
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderDao.getOrderById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();

        order.setUuid(UUID.randomUUID());
        order.setOrderItems(getOrderItemsFromCart(cart, order));
        order.setSubtotal(cart.getSubTotalPrice());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(priceCalculator.calculateTotalPrice(cart));
        order.setStatus(OrderStatus.NEW);

        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        checkOrderItems(order.getOrderItems());
        orderDao.save(order);
        stockService.decreaseProductStock(order.getOrderItems().stream()
                .collect(Collectors.toMap(orderItem -> orderItem.getPhone().getId(), OrderItem::getQuantity)));

        LOGGER.info("Order with id = {}, uuid = {}, subtotal = {}, deliveryPrice = {}, totalPrice = {}, firstName = {}, " +
                "lastName = {}, contactPhoneNo = {}, deliveryAddress = {} was created", order.getId(), order.getUuid(),
                order.getSubtotal(), order.getDeliveryPrice(), order.getTotalPrice(), order.getFirstName(), order.getLastName(),
                order.getContactPhoneNo(), order.getDeliveryAddress());
    }

    private List<OrderItem> getOrderItemsFromCart(Cart cart, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Phone phone = phoneService.getPhoneById(cartItem.getPhoneId());
            orderItems.add(new OrderItem(phone, order, cartItem.getQuantity()));
        }

        return orderItems;
    }

    private void checkOrderItems(List<OrderItem> orderItems) throws OutOfStockException {
        for (OrderItem orderItem : orderItems) {
            Integer stock = stockDao.getStock(orderItem.getPhone().getId()).getStock();

            if (stock < orderItem.getQuantity()) {
                throw new OutOfStockException();
            }
        }
    }

}
