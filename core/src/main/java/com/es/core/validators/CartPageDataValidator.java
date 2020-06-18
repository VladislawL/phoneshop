package com.es.core.validators;

import com.es.core.services.CartPageData;
import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

@Component
public class CartPageDataValidator implements Validator {

    @Autowired
    private StockService stockService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartPageData.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartPageData cartPageData = (CartPageData) o;
        for (Map.Entry<Long, Long> pair : cartPageData.getCartItems().entrySet()) {
            boolean quantityValidationResult = validateQuantity(pair.getKey(), pair.getValue(), errors);

            if (quantityValidationResult) {
                validateStock(pair.getKey(), pair.getValue(), errors);
            }
        }
    }

    private boolean validateQuantity(Long phoneId, Long quantity, Errors errors) {
        if (quantity == null) {
            errors.rejectValue("cartItems[" + phoneId + "]", "", "must not be blank");
            return false;
        }

        if (quantity < 1) {
            errors.rejectValue("cartItems[" + phoneId + "]", "", "must be a positive number");
            return false;
        }

        return true;
    }

    private boolean validateStock(Long phoneId, Long quantity, Errors errors) {
        Long stock = stockService.getStock(phoneId);

        if (quantity > stock) {
            errors.rejectValue("cartItems[" + phoneId + "]", "", "Not enough stock, available " + stock);
            return false;
        }

        return true;
    }

}
