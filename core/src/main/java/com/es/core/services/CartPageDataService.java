package com.es.core.services;

import com.es.core.cart.CartPageData;

import java.util.Map;

public interface CartPageDataService {
    CartPageData createCartPageData();
    Map<Long, Long> convertCartItems(CartPageData cartPageData);
}
