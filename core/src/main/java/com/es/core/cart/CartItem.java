package com.es.core.cart;

import com.es.core.validators.QuantityConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@QuantityConstraint(phoneId = "phoneId", quantity = "quantity")
public class CartItem implements Serializable {

    @NotNull
    private long phoneId;

    @NotNull
    @Positive
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
}
