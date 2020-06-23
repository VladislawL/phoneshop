package com.es.core.cart;

import org.springframework.validation.ObjectError;

import java.util.List;

public class CartItemValidationException extends RuntimeException {

    private List<ObjectError> validationErrors;

    public CartItemValidationException(List<ObjectError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<ObjectError> getValidationErrors() {
        return validationErrors;
    }
}
