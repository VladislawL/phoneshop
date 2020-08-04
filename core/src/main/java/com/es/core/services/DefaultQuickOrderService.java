package com.es.core.services;

import com.es.core.cart.CartItem;
import com.es.core.model.order.QuickOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DefaultQuickOrderService implements QuickOrderService {

    @Value("${number.of.quick.orders}")
    private Long numberOfQuickOrders;

    @Override
    public QuickOrder getEmptyQuickOrder() {
        QuickOrder quickOrder = new QuickOrder();
        ArrayList<CartItem> cartItems = new ArrayList<>();

        for (int i = 0; i < numberOfQuickOrders.intValue(); i++) {
            cartItems.add(new CartItem());
        }

        quickOrder.setCartItems(cartItems);
        return quickOrder;
    }
}
