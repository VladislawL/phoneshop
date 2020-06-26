package com.es.phoneshop.web.controller.advicer;

import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.QuantityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AjaxExceptionHandler {

    @Autowired
    private ExceptionFormatter exceptionFormatter;

    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<AjaxErrorMessage> handleCartItemValidationException(CartItemValidationException e) {
        AjaxErrorMessage ajaxErrorMessage = exceptionFormatter.formatValidationErrors(e.getValidationErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ajaxErrorMessage);
    }

    @ExceptionHandler(QuantityValidationException.class)
    public ResponseEntity<AjaxErrorMessage> handleQuantityValidationException(QuantityValidationException e) {
        AjaxErrorMessage ajaxErrorMessage = exceptionFormatter
                .formatExceptionMessageUsingCode(e.getCode(), new Long[]{e.getStock()});
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ajaxErrorMessage);
    }

}
