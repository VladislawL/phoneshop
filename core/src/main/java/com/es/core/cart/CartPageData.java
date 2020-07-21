package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartPageData {

    private Map<Phone, Long> cartItems;
    private BigDecimal subTotalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;

    public CartPageData() {
        cartItems = new HashMap<>();
        subTotalPrice = BigDecimal.ZERO;
    }

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

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
