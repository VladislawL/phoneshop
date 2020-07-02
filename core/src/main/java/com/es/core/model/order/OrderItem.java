package com.es.core.model.order;

import com.es.core.model.phone.Phone;

public class OrderItem {
    private Phone phone;
    private Order order;
    private Long quantity;

    public OrderItem() {
    }

    public OrderItem(Phone phone, Order order, Long quantity) {
        this.phone = phone;
        this.order = order;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }
}
