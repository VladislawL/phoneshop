package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneNotFoundException;
import com.es.core.model.phone.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext-core.xml", "classpath:context/coreApplicationContext-test.xml"})
public class DefaultPhoneServiceTest {

    @Autowired
    private DefaultPhoneService phoneService;

    @Value("#{propertySource['phonesOnPage']}")
    private int phonesOnPage;

    @Test
    public void shouldGetPagesCount() {
        int pages = phoneService.getPagesCount("");

        assertThat(pages).isEqualTo(1);
    }

    @Test
    public void shouldGetPhonePage() {
        List<Phone> phones = phoneService.getPhonePage(0, "", "model", SortOrder.DESC);

        assertThat(phones).asList()
                .hasSizeBetween(1, phonesOnPage);
    }

    @Test
    public void shouldGetPhoneById() {
        Phone phone = phoneService.getPhoneById(1L);

        assertThat(phone)
                .isNotNull()
                .matches(p -> p.getId().equals(1L));
    }

    @Test(expected = PhoneNotFoundException.class)
    public void shouldThrowPhoneNotFoundException() {
        phoneService.getPhoneById(10L);
    }

}
