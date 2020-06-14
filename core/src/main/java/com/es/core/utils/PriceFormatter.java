package com.es.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Service
public class PriceFormatter {

    @Autowired
    private Currency defaultCurrency;

    public String format(BigDecimal price, Locale locale, Currency currency) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setCurrency(currency);
        return formatter.format(price);
    }

    public String format(BigDecimal price, Locale locale) {
        return format(price, locale, defaultCurrency);
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }
}
