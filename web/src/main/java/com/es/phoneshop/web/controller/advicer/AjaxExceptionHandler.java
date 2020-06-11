package com.es.phoneshop.web.controller.advicer;

import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.QuantityValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AjaxExceptionHandler {

    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<AjaxErrorMessage> handleCartItemValidationException(Exception e) {
        AjaxErrorMessage ajaxErrorMessage = new AjaxErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ajaxErrorMessage);
    }

    @ExceptionHandler(QuantityValidationException.class)
    public ResponseEntity<AjaxErrorMessage> handleQuantityValidationException(Exception e) {
        AjaxErrorMessage ajaxErrorMessage = new AjaxErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ajaxErrorMessage);
    }

}
