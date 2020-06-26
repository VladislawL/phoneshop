package com.es.core.cart;

public class QuantityValidationException extends RuntimeException {

    private String code;
    private Long stock;

    public QuantityValidationException(String code, Long stock) {
        this.code = code;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public Long getStock() {
        return stock;
    }
}
