package com.es.phoneshop.web.controller.advicer;

import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.QuantityValidationException;
import com.es.phoneshop.web.config.AjaxErrorMessageQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class AjaxExceptionHandler {

    @Autowired
    @AjaxErrorMessageQualifier
    private Map<String, Object> errorMessage;

    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<Map<String, Object>> handleCartItemValidationException(Exception e) {
        errorMessage.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(QuantityValidationException.class)
    public ResponseEntity<Map<String, Object>> handleQuantityValidationException(Exception e) {
        errorMessage.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
