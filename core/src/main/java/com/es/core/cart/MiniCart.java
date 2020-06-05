package com.es.core.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MiniCart {

    private Map<String, Object> miniCart;

    public MiniCart() {
        miniCart = new HashMap<>();
        miniCart.put("itemsNumber", 0);
        miniCart.put("subTotalPrice", BigDecimal.ZERO);
    }

    public void setItemsNumber(int n) {
        miniCart.put("itemsNumber", n);
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        miniCart.put("subTotalPrice", subTotalPrice);
    }

    public Map<String, Object> getMiniCart() {
        return miniCart;
    }

}
