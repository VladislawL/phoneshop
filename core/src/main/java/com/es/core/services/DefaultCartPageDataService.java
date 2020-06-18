package com.es.core.services;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultCartPageDataService implements CartPageDataService {

    @Autowired
    private CartService cartService;

    @Override
    public void fillCartPageData(CartPageData cartPageData) {
        Map<Long, Long> cartItems = cartService.getCart().getCartItems().stream()
                .collect(Collectors.toMap(CartItem::getPhoneId, CartItem::getQuantity));
        cartPageData.setCartItems(cartItems);
    }

}
