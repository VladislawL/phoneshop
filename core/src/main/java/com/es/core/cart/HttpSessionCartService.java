package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private Cart cart;

    @Autowired
    private PhoneService phoneService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        if (cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getPhoneId().equals(phoneId))) {
            Map<Long, Long> cartItem = new HashMap<>();
            cartItem.put(phoneId, quantity);
            update(cartItem);
        } else {
            List<CartItem> cartItems = cart.getCartItems();
            cartItems.add(new CartItem(phoneId, quantity));
            cart.setSubTotalPrice(calculateSubTotalPrice());
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Long phoneId: items.keySet()) {
            Optional<CartItem> cartItem = findCartItem(phoneId);
            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(items.get(phoneId));
            } else {
                addPhone(phoneId, items.get(phoneId));
            }
        }
        cart.setSubTotalPrice(calculateSubTotalPrice());
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
    }

    private BigDecimal calculateSubTotalPrice() {
        List<CartItem> cartItems = cart.getCartItems();
        BigDecimal subTotalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Phone phone = phoneService.getPhoneById(cartItem.getPhoneId());
            subTotalPrice = subTotalPrice.add(phone.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        return subTotalPrice;
    }

    private Optional<CartItem> findCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getPhoneId().equals(phoneId))
                .findFirst();
    }

}
