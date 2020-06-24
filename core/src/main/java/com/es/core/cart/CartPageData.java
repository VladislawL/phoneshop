package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CartPageData {

    private Map<Long, Long> cartItems;
    private List<Phone> phones;
    private BigDecimal subTotalPrice;

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
