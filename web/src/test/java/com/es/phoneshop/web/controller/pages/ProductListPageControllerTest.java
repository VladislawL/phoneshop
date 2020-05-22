package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageControllerTest {
    @Mock
    private PhoneDao phoneDao;
    @InjectMocks
    private ProductListPageController controller;

    @Test
    public void testShowProductList() throws Exception {
        List<Phone> expectedPhoneList = createPhoneList(10);

        when(phoneDao.findAll(Mockito.eq(0), Mockito.eq(10))).thenReturn(expectedPhoneList);

        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/pages/productList.jsp"))
                .build();

        mockMvc.perform(get("/productList"))
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("phones"))
                .andExpect(model().attribute("phones", expectedPhoneList));
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
