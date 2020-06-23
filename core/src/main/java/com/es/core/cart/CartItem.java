package com.es.core.cart;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {

    @NotNull(message = "{field.required}")
    private long phoneId;

    @NotNull
    @PositiveOrZero(message = "{negative.quantity}")
    private long quantity;

    public CartItem() {
    }

    public CartItem(long phoneId, long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return phoneId == cartItem.phoneId &&
                quantity == cartItem.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneId, quantity);
    }
}
