package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageData;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartPageDataServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private PhoneService phoneService;

    @Mock
    private Cart cart;

    @InjectMocks
    private DefaultCartPageDataService cartPageDataService;

    @Test
    public void shouldCreateCartPageData() {
        long phoneId = 1L;
        CartItem cartItem = new CartItem(phoneId, 1L);
        Phone phone = new Phone();
        phone.setId(phoneId);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        BigDecimal subTotalPrice = BigDecimal.ZERO;
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);

        Map<Phone, Long> expectedMap = new HashMap<>();
        expectedMap.put(phone, cartItem.getQuantity());

        when(cartService.getCart()).thenReturn(cart);
        when(phoneService.getPhonesById(Mockito.any())).thenReturn(phones);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(cart.getSubTotalPrice()).thenReturn(subTotalPrice);

        CartPageData cartPageData = cartPageDataService.createCartPageData();

        assertThat(expectedMap).isEqualTo(cartPageData.getCartItems());
        assertThat(subTotalPrice).isEqualTo(cartPageData.getSubTotalPrice());
    }

}
