package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageData;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultCartPageDataService implements CartPageDataService {

    @Autowired
    private CartService cartService;

    @Autowired
    private PhoneService phoneService;

    @Override
    public CartPageData createCartPageData() {
        CartPageData cartPageData = new CartPageData();
        Cart cart = cartService.getCart();
        List<CartItem> values = cart.getCartItems();

        List<Phone> phones = phoneService.getPhonesById(values.stream()
                .map(CartItem::getPhoneId)
                .collect(Collectors.toList()));

        Map<Phone, Long> cartItems = new HashMap<>();

        for (Phone phone : phones) {
            cartItems.put(phone, getCartItemByPhoneId(values, phone.getId()).getQuantity());
        }

        cartPageData.setSubTotalPrice(cart.getSubTotalPrice());
        cartPageData.setDeliveryPrice(cart.getDeliveryPrice());
        cartPageData.setTotalPrice(cart.getTotalPrice());
        cartPageData.setCartItems(cartItems);

        return cartPageData;
    }

    @Override
    public Map<Long, Long> convertCartItems(CartPageData cartPageData) {
        return cartPageData.getCartItems().entrySet().stream()
                .collect(Collectors.toMap(entry-> entry.getKey().getId(), Map.Entry::getValue));
    }

    private CartItem getCartItemByPhoneId(List<CartItem> cartItems, Long id) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getPhoneId().equals(id))
                .findFirst().get();
    }

}
