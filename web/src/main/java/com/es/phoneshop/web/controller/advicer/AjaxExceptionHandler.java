package com.es.phoneshop.web.controller.advicer;

import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.QuantityValidationException;
import com.es.core.validators.QuantityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class AjaxExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<AjaxErrorMessage> handleCartItemValidationException(Exception e) {
        AjaxErrorMessage ajaxErrorMessage = new AjaxErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ajaxErrorMessage);
    }

    @ExceptionHandler(QuantityValidationException.class)
    public ResponseEntity<AjaxErrorMessage> handleQuantityValidationException(QuantityValidationException e) {
        String message = createMessage(e);
        AjaxErrorMessage ajaxErrorMessage = new AjaxErrorMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ajaxErrorMessage);
    }

    private String createMessage(QuantityValidationException e) {
        String messageCode = e.getCode();
        Long[] args = new Long[]{e.getStock()};
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, args, locale);
    }

}
