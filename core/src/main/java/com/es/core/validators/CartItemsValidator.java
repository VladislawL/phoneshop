package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.model.order.OrderItem;
import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CartItemsValidator implements Validator {

    @Autowired
    private StockService stockService;

    private static final String INVALID_ORDER_ITEMS_VALIDATION_MESSAGE = "You're going to order as much phones as we don't have. Go back to cart to fix";

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderItem.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        List<CartItem>  cartItems = (List<CartItem>) o;
        for (CartItem cartItem : cartItems) {
            if (stockService.getStock(cartItem.getPhoneId()) < cartItem.getQuantity()) {
                errors.rejectValue("cartItems", "invalid.order.items", INVALID_ORDER_ITEMS_VALIDATION_MESSAGE);
                break;
            }
        }
    }

}
