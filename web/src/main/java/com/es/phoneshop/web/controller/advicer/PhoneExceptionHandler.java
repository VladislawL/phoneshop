package com.es.phoneshop.web.controller.advicer;

import com.es.core.model.phone.PhoneNotFoundException;
import com.es.core.order.OrderNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PhoneExceptionHandler {

    @ExceptionHandler({PhoneNotFoundException.class, OrderNotFoundException.class})
    public String handleNotFoundException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "notFoundPage";
    }

}
