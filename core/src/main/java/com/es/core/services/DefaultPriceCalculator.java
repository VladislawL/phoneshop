package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultPriceCalculator implements PriceCalculator {

    @Autowired
    private PhoneService phoneService;

    @Override
    public BigDecimal calculateSubtotalPrice(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();

        BigDecimal subTotalPrice = cartItems.stream()
                .map(item -> phoneService.getPhoneById(
                        item.getPhoneId()).getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        return subTotalPrice;
    }

}
