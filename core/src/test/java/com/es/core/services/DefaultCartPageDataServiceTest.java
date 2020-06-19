package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartPageDataServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private Cart cart;

    @Mock
    private CartPageData cartPageData;

    @InjectMocks
    private DefaultCartPageDataService cartPageDataService;

    @Captor
    private ArgumentCaptor<Map<Long, Long>> mapArgumentCaptor;

    @Test
    public void shouldFillCartPageData() {
        CartItem cartItem1 = new CartItem(1L, 1L);
        CartItem cartItem2 = new CartItem(2L, 2L);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        Map<Long, Long> expectedMap = new HashMap<>();
        expectedMap.put(cartItem1.getPhoneId(), cartItem1.getQuantity());
        expectedMap.put(cartItem2.getPhoneId(), cartItem2.getQuantity());

        when(cartService.getCart()).thenReturn(cart);
        when(cart.getCartItems()).thenReturn(cartItems);

        cartPageDataService.fillCartPageData(cartPageData);

        verify(cartPageData).setCartItems(mapArgumentCaptor.capture());
        assertThat(expectedMap).isEqualTo(mapArgumentCaptor.getValue());
    }

}
