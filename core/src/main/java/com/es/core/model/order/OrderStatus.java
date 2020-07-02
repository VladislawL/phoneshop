package com.es.core.model.order;

public enum OrderStatus {
    NEW(0), DELIVERED(1), REJECTED(2);
    private int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
