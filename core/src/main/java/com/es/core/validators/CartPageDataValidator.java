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
    private QuantityValidator quantityValidator;

    @Autowired
    private StockService stockService;

    private static final String QUANTITY_IS_EMPTY_MESSAGE = "Must not be blank";
    private static final String QUANTITY_IS_NEGATIVE_MESSAGE = "Must be a positive number";
    private static final String QUANTITY_GREATER_THAN_STOCK_MESSAGE = "Not enough stock";

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
            errors.rejectValue("cartItems[" + phoneId + "]", "field.required", QUANTITY_IS_EMPTY_MESSAGE);
            return false;
        }

        if (quantity < 0) {
            errors.rejectValue("cartItems[" + phoneId + "]", "negative.quantity", QUANTITY_IS_NEGATIVE_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateStock(Long phoneId, Long quantity, Errors errors) {
        if (!quantityValidator.isValid(phoneId, quantity)) {
            Long[] args = new Long[]{stockService.getStock(phoneId)};
            errors.rejectValue("cartItems[" + phoneId + "]", "quantity.greaterThanStock", args , QUANTITY_GREATER_THAN_STOCK_MESSAGE);
            return false;
        }

        return true;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void setQuantityValidator(QuantityValidator quantityValidator) {
        this.quantityValidator = quantityValidator;
    }
}
