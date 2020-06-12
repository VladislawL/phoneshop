package com.es.core.cart;

import com.es.core.services.PriceCalculator;
import com.es.core.services.StockService;
import com.es.core.validators.QuantityValidator;
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
import java.util.Collections;
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
    private PriceCalculator priceCalculatorService;

    @Mock
    private StockService stockService;

    @Mock
    private QuantityValidator quantityValidator;

    @Spy
    private List<CartItem> cartItems;

    @InjectMocks
    private HttpSessionCartService httpSessionCartService;

    @Before
    public void setup() {
        long phoneId = 1L;
        long quantity = 1L;
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem(phoneId, quantity));

        when(cart.getCartItems()).thenReturn(cartItems);
    }

    @Test
    public void shouldAddCartItemIfNotExist() {
        long phoneId = 2L;
        long quantity = 1L;

        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(true);

        httpSessionCartService.addPhone(phoneId, quantity);

        verify(priceCalculatorService).calculateSubtotalPrice(cart);
        assertThat(cartItems).asList()
                .hasSize(2)
                .contains(new CartItem(phoneId, quantity));
    }

    @Test(expected = QuantityValidationException.class)
    public void shouldThrowQuantityValidationExceptionIfQuantityGreaterThenStockInAddPhone() {
        long phoneId = 2L;
        long quantity = 100L;

        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(false);
        when(stockService.getStock(Mockito.anyLong())).thenReturn(0L);

        httpSessionCartService.addPhone(phoneId, quantity);
    }

    @Test(expected = QuantityValidationException.class)
    public void shouldThrowQuantityValidationExceptionIfQuantityGreaterThenStockInUpdatePhone() {
        long phoneId = 2L;
        long quantity = 100L;
        Map<Long, Long> cartItem = Collections.singletonMap(phoneId, quantity);

        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(false);
        when(stockService.getStock(Mockito.anyLong())).thenReturn(0L);

        httpSessionCartService.updatePhone(cartItem);
    }

    @Test
    public void shouldUpdateCartItemIfExistInAddPhoneMethod() {
        long phoneId = 1L;
        long quantity = 3L;

        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(true);

        httpSessionCartService.addPhone(phoneId, quantity);

        verify(priceCalculatorService).calculateSubtotalPrice(cart);
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

        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(true);

        httpSessionCartService.updatePhone(cartItem);

        verify(priceCalculatorService).calculateSubtotalPrice(cart);
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

        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(true);

        httpSessionCartService.updatePhone(cartItem);

        verify(priceCalculatorService).calculateSubtotalPrice(cart);
        assertThat(cartItems).asList()
                .hasSize(2)
                .contains(new CartItem(phoneId, quantity));
    }

    @Test
    public void shouldRemovePhone() {
        long phoneId = 1L;

        httpSessionCartService.remove(phoneId);

        verify(priceCalculatorService).calculateSubtotalPrice(cart);
        assertThat(cartItems).asList()
                .hasSize(0);
    }
}
