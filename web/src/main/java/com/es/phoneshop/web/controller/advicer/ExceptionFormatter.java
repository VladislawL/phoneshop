package com.es.phoneshop.web.controller.advicer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;

@Component
public class ExceptionFormatter {

    @Autowired
    private MessageSource messageSource;

    public String formatValidationErrors(List<ObjectError> validationErrors) {
        StringBuilder message = new StringBuilder(validationErrors.get(0).getDefaultMessage());

        for (int i = 1; i < validationErrors.size(); i++) {
            message.append(", ").append(validationErrors.get(i).getDefaultMessage());
        }

        return new String(message);
    }

    public String formatExceptionMessageUsingCode(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }

}
