package com.es.core.cart;

import com.es.core.model.phone.Phone;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    List<Phone> getPhones();

    /**
     * @param items key: {@link com.es.core.model.phone.Phone#id}
     *              value: quantity
     */
    void updatePhone(Map<Long, Long> items);

    void remove(Long phoneId);

    void addCartItems(List<CartItem> cartItems, Errors errors);
}
