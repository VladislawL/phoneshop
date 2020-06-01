package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class AjaxCartControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnSubtotalPriceAndNumberOfItemsIfThereAreNoValidationErrors() throws Exception {
        long phoneId = 1L;
        long quantity = 1L;
        CartItem cartItem = new CartItem(phoneId, quantity);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCartItem = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(post("/ajaxCart")
                .content(jsonCartItem)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subTotalPrice", equalTo(BigDecimal.ONE + ".0")))
                .andExpect(jsonPath("$.itemsNumber", equalTo(1)));
    }

    @Test
    public void shouldReturnPositiveConstraintMessageIfQuantityLessThanOne() throws Exception {
        long phoneId = 1L;
        long quantity = -1L;
        CartItem cartItem = new CartItem(phoneId, quantity);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCartItem = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(post("/ajaxCart")
                .content(jsonCartItem)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("must be a positive number")));
    }

}
