package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.Map;

public class CartPageData {

    private Map<Phone, Long> cartItems;
    private BigDecimal subTotalPrice;

    public Map<Phone, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Phone, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
