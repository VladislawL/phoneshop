package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Attribute;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortOrder;
import com.es.core.services.AttributeService;
import com.es.core.services.PhoneService;
import com.es.core.utils.PriceFormatter;
import com.es.phoneshop.web.pagedata.PaginationData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class ProductListPageControllerTest {

    @Mock
    private PhoneService phoneService;

    @Mock
    private PaginationData paginationData;

    @Mock
    private AttributeService attributeService;

    @InjectMocks
    private ProductListPageController controller;

    @Test
    public void testShowProductList() throws Exception {
        int pagesCount = 1;
        int currentPage = 1;
        String query = "test";
        String sortField = "price";
        SortOrder sortOrder = SortOrder.DESC;

        List<Phone> expectedPhoneList = createPhoneList(10);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("attr", "attr", true));

        doNothing().when(paginationData).setPagesCount(Mockito.anyInt());
        doNothing().when(paginationData).setCurrentPage(Mockito.anyInt());
        doNothing().when(paginationData).setPhones(Mockito.any());
        when(phoneService.getPagesCount(Mockito.anyString())).thenReturn(pagesCount);
        when(phoneService.getPhonePage(Mockito.eq(currentPage), Mockito.eq(query), Mockito.eq(sortField), Mockito.eq(sortOrder))).thenReturn(expectedPhoneList);
        when(attributeService.getAttributes()).thenReturn(attributes);

        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/pages/productList.jsp"))
                .build();

        mockMvc.perform(get("/productList")
                .param("query", query)
                .param("sortField", sortField)
                .param("sortOrder", sortOrder.name())
                .param("page", Integer.toString(currentPage)))
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("paginationData"))
                .andExpect(model().attribute("paginationData", equalTo(paginationData)))
                .andExpect(model().attribute("attributes", equalTo(attributes)));
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
