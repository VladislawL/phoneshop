package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
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
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPriceCalculatorTest {

    @Mock
    private PhoneService phoneService;

    @Mock
    private Cart cart;

    @InjectMocks
    private DefaultPriceCalculator priceCalculator;

    @Captor
    private ArgumentCaptor<BigDecimal> subTotalPriceArgumentCaptor;

    @Test
    public void shouldCalculateSubTotalPrice() {
        List<CartItem> cartItems = new ArrayList<>();
        long phoneId1 = 1L;
        long phoneId2 = 2L;
        long quantity1 = 1L;
        long quantity2 = 2L;
        Phone phone1 = createTestPhone(phoneId1, BigDecimal.ONE);
        Phone phone2 = createTestPhone(phoneId2, BigDecimal.ONE);

        cartItems.add(new CartItem(phoneId1, quantity1));
        cartItems.add(new CartItem(phoneId2, quantity2));

        when(cart.getCartItems()).thenReturn(cartItems);
        when(phoneService.getPhoneById(phoneId1)).thenReturn(phone1);
        when(phoneService.getPhoneById(phoneId2)).thenReturn(phone2);

        priceCalculator.calculateSubtotalPrice(cart);

        verify(cart).setSubTotalPrice(subTotalPriceArgumentCaptor.capture());

        assertThat(subTotalPriceArgumentCaptor.getValue()).isEqualTo(BigDecimal.valueOf(3));
    }

    private Phone createTestPhone(Long id, BigDecimal price) {
        Phone phone = new Phone();

        phone.setId(id);
        phone.setPrice(price);

        return phone;
    }

}
