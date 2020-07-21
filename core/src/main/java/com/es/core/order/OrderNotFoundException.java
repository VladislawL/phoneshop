package com.es.core.order;

public class OrderNotFoundException extends RuntimeException {

    private String orderId;

    private static final String ERROR_MESSAGE = "Order with id = %s not found";

    public OrderNotFoundException(String orderId) {
        super(String.format(ERROR_MESSAGE, orderId));
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
