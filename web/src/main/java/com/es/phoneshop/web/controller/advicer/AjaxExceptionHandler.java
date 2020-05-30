package com.es.phoneshop.web.controller.advicer;

import com.es.core.cart.CartItemValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AjaxExceptionHandler {

    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<Map<String, Object>> handleCartItemValidationException(Exception e) {
        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
