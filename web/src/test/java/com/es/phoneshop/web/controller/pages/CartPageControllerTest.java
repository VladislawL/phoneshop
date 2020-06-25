package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Attribute;
import com.es.core.model.phone.Phone;
import com.es.core.services.AttributeService;
import com.es.core.cart.CartPageData;
import com.es.core.services.CartPageDataService;
import com.es.core.services.StockService;
import com.es.core.validators.CartPageDataValidator;
import com.es.core.validators.QuantityValidator;
import com.es.phoneshop.web.controller.converter.StringIdToPhone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CartPageControllerTest {
    @Mock
    private CartService cartService;

    @Mock
    private AttributeService attributeService;

    @Mock
    private CartPageDataService cartPageDataService;

    @Mock
    private StockService stockService;

    @Mock
    private QuantityValidator quantityValidator;

    @Mock
    private Cart cart;

    @Spy
    private CartPageDataValidator cartPageDataValidator;

    @Mock
    private StringIdToPhone stringIdToPhone;

    @InjectMocks
    private CartPageController cartPageController;

    private List<Attribute> attributes;
    private FormattingConversionService conversionService;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        Attribute attribute = new Attribute("","",true);
        conversionService = new FormattingConversionService();
        attributes = new ArrayList<>();
        attributes.add(attribute);

        cartPageDataValidator.setStockService(stockService);
        cartPageDataValidator.setQuantityValidator(quantityValidator);

        when(attributeService.getAttributes()).thenReturn(attributes);

        conversionService.addConverter(stringIdToPhone);
        mockMvc = standaloneSetup(cartPageController)
                .setConversionService(conversionService)
                .build();
    }

    @Test
    public void shouldReturnCartPage() throws Exception {
        long phoneId = 1L;
        long quantity = 1L;
        Phone phone = new Phone();
        phone.setId(phoneId);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(Collections.singletonMap(phone, quantity));

        when(cartPageDataService.createCartPageData()).thenReturn(cartPageData);

        mockMvc.perform(get("/cart"))
                .andExpect(model().attributeExists("cartPageData"))
                .andExpect(model().attribute("attributes", equalTo(attributes)))
                .andExpect(view().name("cartPage"));
    }

    @Test
    public void shouldRedirectIfThereWereNoMistakes() throws Exception {
        doNothing().when(cartPageDataValidator).validate(Mockito.any(), Mockito.any());

        mockMvc.perform(post("/cart"))
                .andExpect(redirectedUrl("cart"));
    }

    @Test
    public void shouldReturnCartPageIfQuantityWasNegativeMistakes() throws Exception {
        long phoneId = 1L;
        long quantity = -1;
        Phone phone = new Phone();
        phone.setId(phoneId);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(Collections.singletonMap(phone, quantity));

        when(cart.getSubTotalPrice()).thenReturn(BigDecimal.ZERO);
        when(stringIdToPhone.convert(Long.toString(phoneId))).thenReturn(phone);

        mockMvc.perform(post("/cart")
                .param("cartItems["+ phoneId + "]", Long.toString(quantity)))
                .andExpect(model().attributeHasFieldErrorCode("cartPageData", "cartItems[1]", "negative.quantity"))
                .andExpect(view().name("cartPage"));
    }

    @Test
    public void shouldReturnCartPageIfQuantityWasGreaterThanStockMistakes() throws Exception {
        long phoneId = 1L;
        long quantity = 1;
        Phone phone = new Phone();
        phone.setId(phoneId);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(Collections.singletonMap(phone, quantity));

        when(stockService.getStock(Mockito.eq(phoneId))).thenReturn(0L);
        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(false);
        when(cart.getSubTotalPrice()).thenReturn(BigDecimal.ZERO);
        when(stringIdToPhone.convert(Long.toString(phoneId))).thenReturn(phone);

        mockMvc.perform(post("/cart")
                .param("cartItems["+ phoneId + "]", Long.toString(quantity)))
                .andExpect(model().attributeHasFieldErrorCode("cartPageData", "cartItems[1]", "quantity.greaterThanStock"))
                .andExpect(view().name("cartPage"));
    }

}
