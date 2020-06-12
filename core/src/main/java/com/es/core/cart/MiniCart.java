package com.es.core.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MiniCart implements Serializable {

    private BigDecimal subTotalPrice;
    private int itemsNumber;

    public MiniCart() {
        subTotalPrice = BigDecimal.ZERO;
        itemsNumber = 0;
    }

    public MiniCart(BigDecimal subTotalPrice, int itemsNumber) {
        this.subTotalPrice = subTotalPrice;
        this.itemsNumber = itemsNumber;
    }

    public static MiniCart fromCart(Cart cart) {
        MiniCart miniCart = new MiniCart();
        miniCart.setSubTotalPrice(cart.getSubTotalPrice());
        miniCart.setItemsNumber(cart.getCartItems().size());
        return miniCart;
    }

    public void setItemsNumber(int n) {
        itemsNumber = n;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
