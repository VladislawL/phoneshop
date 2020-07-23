package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(value = "classpath:db/test-order.sql")
public class OrdersPageControllerTest  {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private OrderService orderService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(filterChainProxy))
                .build();
    }

    @Test
    public void shouldReturnAdminPage() throws Exception {
        mvc.perform(get("/admin/orders")
                    .with(csrf())
                    .with(user("admin").password("password").roles("ADMIN")))
                .andExpect(view().name("adminPage"));
    }

    @Test
    public void shouldDenyRequest() throws Exception {
        mvc.perform(get("/admin/orders")
                    .with(csrf())
                    .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldReturnAdminOrderPage() throws Exception {
        mvc.perform(get("/admin/orders/1")
                    .with(csrf())
                    .with(user("admin").password("password").roles("ADMIN")))
                .andExpect(view().name("adminOrderPage"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    public void shouldChangeOrderStatus() throws Exception {
        OrderStatus expectedStatus = OrderStatus.DELIVERED;

        ObjectMapper objectMapper = new ObjectMapper();
        String orderStatus = objectMapper.writeValueAsString(expectedStatus);

        mvc.perform(put("/admin/orders/1")
                    .contentType("application/json")
                    .content(orderStatus)
                    .with(csrf())
                    .with(user("admin").password("password").roles("ADMIN")));

        Order newOrder = orderService.getOrderById(1L);
        assertThat(newOrder.getStatus()).isEqualTo(expectedStatus);
    }

}
