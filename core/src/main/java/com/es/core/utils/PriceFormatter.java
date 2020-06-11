package com.es.core.utils;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Service
public class PriceFormatter {
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");

    public String format(BigDecimal price, Locale locale, Currency currency) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setCurrency(currency);
        return formatter.format(price);
    }

    public String format(BigDecimal price, Locale locale) {
        return format(price, locale, DEFAULT_CURRENCY);
    }

    public Currency getDefaultCurrency() {
        return DEFAULT_CURRENCY;
    }
}
