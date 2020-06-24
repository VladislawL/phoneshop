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
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
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

    @InjectMocks
    private DefaultCartPageDataService cartPageDataService;

    @Test
    public void shouldCreateCartPageData() {
        CartItem cartItem1 = new CartItem(1L, 1L);
        CartItem cartItem2 = new CartItem(2L, 2L);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        BigDecimal subTotalPrice = BigDecimal.ZERO;
        List<Phone> phones = createPhoneList(2);

        Map<Long, Long> expectedMap = new HashMap<>();
        expectedMap.put(cartItem1.getPhoneId(), cartItem1.getQuantity());
        expectedMap.put(cartItem2.getPhoneId(), cartItem2.getQuantity());

        when(cartService.getCart()).thenReturn(cart);
        when(cartService.getPhones()).thenReturn(phones);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(cart.getSubTotalPrice()).thenReturn(subTotalPrice);

        CartPageData cartPageData = cartPageDataService.createCartPageData();

        assertThat(expectedMap).isEqualTo(cartPageData.getCartItems());
        assertThat(phones).isEqualTo(cartPageData.getPhones());
        assertThat(subTotalPrice).isEqualTo(cartPageData.getSubTotalPrice());
    }

    private List<Phone> createPhoneList(int count) {
        List<Phone> phones = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Phone phone = new Phone();
            phone.setId((long) i);
            phones.add(phone);
        }

        return phones;
    }

}
