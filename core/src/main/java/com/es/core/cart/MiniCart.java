package com.es.core.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MiniCart {

    private String subTotalPrice;
    private int itemsNumber;

    public MiniCart() {
        subTotalPrice = BigDecimal.ZERO.toString();
        itemsNumber = 0;
    }

    public MiniCart(String subTotalPrice, int itemsNumber) {
        this.subTotalPrice = subTotalPrice;
        this.itemsNumber = itemsNumber;
    }

    public void setItemsNumber(int n) {
        itemsNumber = n;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public String getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
