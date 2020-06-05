package com.es.core.cart;

import org.springframework.validation.ObjectError;

import java.util.List;

public class CartItemValidationException extends RuntimeException {

    private List<ObjectError> validationErrors;

    public CartItemValidationException(List<ObjectError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(validationErrors.get(0).getDefaultMessage());

        for (int i = 1; i < validationErrors.size(); i++)
            message.append(", ").append(validationErrors.get(i).getDefaultMessage());

        return new String(message);
    }
}
