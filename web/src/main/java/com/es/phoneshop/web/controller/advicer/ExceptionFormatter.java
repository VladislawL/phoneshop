package com.es.phoneshop.web.controller.advicer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class ExceptionFormatter {

    @Autowired
    private MessageSource messageSource;

    public String formatValidationErrors(List<ObjectError> validationErrors) {
        return validationErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    public String formatExceptionMessageUsingCode(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }

}
