package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    @Mock
    private Cart cart;

    @Mock
    private PhoneService phoneService;

    @Spy
    private List<CartItem> cartItems;

    @Captor
    private ArgumentCaptor<BigDecimal> subTotalPriceCaptor;

    @InjectMocks
    private HttpSessionCartService httpSessionCartService;

    @Before
    public void setup() {
        long phoneId = 1L;
        long quantity = 1L;
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem(phoneId, quantity));
        Phone phone = createTestPhone(phoneId, BigDecimal.ONE);

        when(cart.getCartItems()).thenReturn(cartItems);
        when(phoneService.getPhoneById(Mockito.eq(phoneId))).thenReturn(phone);
    }

    @Test
    public void shouldAddCartItemIfNotExist() {
        long phoneId = 2L;
        long quantity = 1L;
        Phone phone = createTestPhone(phoneId, BigDecimal.ONE);

        when(phoneService.getPhoneById(Mockito.eq(phoneId))).thenReturn(phone);

        httpSessionCartService.addPhone(phoneId, quantity);

        verify(cart).setSubTotalPrice(subTotalPriceCaptor.capture());
        assertThat(subTotalPriceCaptor.getValue().intValue()).isEqualTo(2);
        assertThat(cartItems).asList()
                .hasSize(2)
                .contains(new CartItem(phoneId, quantity));
    }

    @Test
    public void shouldUpdateCartItemIfExistInAddPhoneMethod() {
        long phoneId = 1L;
        long quantity = 3L;

        httpSessionCartService.addPhone(phoneId, quantity);

        verify(cart).setSubTotalPrice(subTotalPriceCaptor.capture());
        assertThat(subTotalPriceCaptor.getValue().intValue()).isEqualTo(3);
        assertThat(cartItems).asList()
                .hasSize(1)
                .contains(new CartItem(phoneId, quantity));
    }

    @Test
    public void shouldUpdateCartItemIfExist() {
        long phoneId = 1L;
        long quantity = 3L;
        Map<Long, Long> cartItem = new HashMap<>();
        cartItem.put(phoneId, quantity);

        httpSessionCartService.update(cartItem);

        verify(cart).setSubTotalPrice(subTotalPriceCaptor.capture());
        assertThat(subTotalPriceCaptor.getValue().intValue()).isEqualTo(3);
        assertThat(cartItems).asList()
                .hasSize(1)
                .contains(new CartItem(phoneId, quantity));
    }

    @Test
    public void shouldAddCartItemIfNotExistInUpdateMethod() {
        long phoneId = 2L;
        long quantity = 1L;
        Map<Long, Long> cartItem = new HashMap<>();
        cartItem.put(phoneId, quantity);
        Phone phone = createTestPhone(phoneId, BigDecimal.ONE);

        when(phoneService.getPhoneById(Mockito.eq(phoneId))).thenReturn(phone);

        httpSessionCartService.update(cartItem);

        verify(cart).setSubTotalPrice(subTotalPriceCaptor.capture());
        assertThat(subTotalPriceCaptor.getValue().intValue()).isEqualTo(2);
        assertThat(cartItems).asList()
                .hasSize(2)
                .contains(new CartItem(phoneId, quantity));
    }

    @Test
    public void shouldRemovePhone() {
        long phoneId = 1L;

        httpSessionCartService.remove(phoneId);

        verify(cart).setSubTotalPrice(subTotalPriceCaptor.capture());
        assertThat(subTotalPriceCaptor.getValue().intValue()).isEqualTo(0);
        assertThat(cartItems).asList()
                .hasSize(0);
    }

    private Phone createTestPhone(long phoneId, BigDecimal price) {
        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setPrice(price);
        return phone;
    }
}
