package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.dao.PhoneDao;
import com.es.core.model.order.QuickOrder;
import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class QuickOrderValidator implements Validator {

    @Autowired
    private StockService stockService;

    @Autowired
    private PhoneDao phoneDao;

    private static final String CART_ITEM_QUANTITY_FIELD = "cartItems[%s].quantity";
    private static final String CART_ITEM_PHONE_ID_FIELD = "cartItems[%s].phoneId";

    private static final String QUANTITY_VALIDATION_ERROR = "quantity.greaterThanStock";
    private static final String PHONE_ID_VALIDATION_ERROR = "phone.not.exist";

    @Override
    public boolean supports(Class<?> aClass) {
        return QuickOrder.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickOrder quickOrder = (QuickOrder) o;
        List<CartItem> cartItems = quickOrder.getCartItems();
        int i = 0;
        for (CartItem cartItem : cartItems) {
            if (!phoneDao.get(cartItem.getPhoneId()).isPresent()) {
                errors.rejectValue(String.format(CART_ITEM_PHONE_ID_FIELD, i), PHONE_ID_VALIDATION_ERROR);
            } else {
                if (stockService.getStock(cartItem.getPhoneId()) < cartItem.getQuantity()) {
                    errors.rejectValue(String.format(CART_ITEM_QUANTITY_FIELD, i),
                            QUANTITY_VALIDATION_ERROR,
                            new Object[]{stockService.getStock(cartItem.getPhoneId())},
                            null);
                }
            }
            i++;
        }
    }
}
