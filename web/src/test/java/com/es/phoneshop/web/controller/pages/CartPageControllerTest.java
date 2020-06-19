package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.Attribute;
import com.es.core.model.phone.Phone;
import com.es.core.services.AttributeService;
import com.es.core.services.CartPageData;
import com.es.core.services.CartPageDataService;
import com.es.core.services.StockService;
import com.es.core.utils.PriceFormatter;
import com.es.core.validators.CartPageDataValidator;
import com.es.core.validators.QuantityValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private PriceFormatter priceFormatter;

    @Mock
    private CartPageDataService cartPageDataService;

    @Mock
    private StockService stockService;

    @Mock
    private QuantityValidator quantityValidator;

    @Spy
    private CartPageDataValidator cartPageDataValidator;

    @InjectMocks
    private CartPageController cartPageController;

    private Currency currency;
    private List<Phone> phones;
    private List<Attribute> attributes;

    @Before
    public void setup() {
        Attribute attribute = new Attribute("","",true);
        attributes = new ArrayList<>();
        attributes.add(attribute);
        phones = createPhoneList(1);
        currency = Currency.getInstance("USD");

        cartPageDataValidator.setStockService(stockService);
        cartPageDataValidator.setQuantityValidator(quantityValidator);

        when(attributeService.getAttributes()).thenReturn(attributes);
        when(cartService.getPhones()).thenReturn(phones);
        when(priceFormatter.getDefaultCurrency()).thenReturn(currency);
    }

    @Test
    public void shouldReturnCartPage() throws Exception {
        long phoneId = 1L;
        long quantity = 1L;
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(Collections.singletonMap(phoneId, quantity));

        MockMvc mockMvc = standaloneSetup(cartPageController)
                .setSingleView(new InternalResourceView("/WEB-INF/pages/cartPage.jsp"))
                .build();

        mockMvc.perform(get("/cart"))
                .andExpect(model().attributeExists("cartPageData"))
                .andExpect(model().attribute("attributes", equalTo(attributes)))
                .andExpect(model().attribute("currencySymbol", equalTo(currency)))
                .andExpect(model().attribute("phones", equalTo(phones)))
                .andExpect(view().name("cartPage"));
    }

    @Test
    public void shouldRedirectIfThereWereNoMistakes() throws Exception {
        doNothing().when(cartPageDataValidator).validate(Mockito.any(), Mockito.any());

        MockMvc mockMvc = standaloneSetup(cartPageController).build();

        mockMvc.perform(put("/cart"))
                .andExpect(redirectedUrl("cart"));
    }

    @Test
    public void shouldReturnCartPageIfQuantityWasNegativeMistakes() throws Exception {
        long phoneId = 1L;
        long quantity = -1;
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(Collections.singletonMap(phoneId, quantity));

        MockMvc mockMvc = standaloneSetup(cartPageController).build();

        mockMvc.perform(put("/cart")
                .param("cartItems["+ phoneId + "]", Long.toString(quantity)))
                .andExpect(model().attributeHasFieldErrorCode("cartPageData", "cartItems[1]", "negative.quantity"))
                .andExpect(view().name("cartPage"));
    }

    @Test
    public void shouldReturnCartPageIfQuantityWasGreaterThanStockMistakes() throws Exception {
        long phoneId = 1L;
        long quantity = 1;
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(Collections.singletonMap(phoneId, quantity));

        when(stockService.getStock(Mockito.eq(phoneId))).thenReturn(0L);
        when(quantityValidator.isValid(Mockito.eq(phoneId), Mockito.eq(quantity))).thenReturn(false);

        MockMvc mockMvc = standaloneSetup(cartPageController).build();

        mockMvc.perform(put("/cart")
                .param("cartItems["+ phoneId + "]", Long.toString(quantity)))
                .andExpect(model().attributeHasFieldErrorCode("cartPageData", "cartItems[1]", "quantity.greaterThanStock"))
                .andExpect(view().name("cartPage"));
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
