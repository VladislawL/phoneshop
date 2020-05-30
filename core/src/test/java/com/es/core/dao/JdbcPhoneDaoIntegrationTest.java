package com.es.core.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcPhoneDaoIntegrationTest extends AbstractDataBaseIntegrationTest {

    @Autowired
    private PhoneDao phoneDao;

    @Test
    public void shouldFindLimitedPhoneList() {
        int limit = 2;
        List<Phone> phones = phoneDao.findAll(0, limit);

        assertThat(phones).isNotNull()
                .hasSize(2)
                .allMatch(Objects::nonNull);
    }

    @Test
    public void shouldGetExitingPhoneById() {
        Optional<Phone> phone = phoneDao.get(1L);

        assertThat(phone).isNotEmpty();
    }

    @Test
    public void shouldGetEmptyOptionalIfPhoneNotFound() {
        Optional<Phone> phone = phoneDao.get(4L);

        assertThat(phone).isEmpty();
    }

    @Test
    public void shouldSavePhone() {
        String brand = "save";
        String model = "save";
        Long colorId = 1L;
        Set<Color> colors = new HashSet<>();
        colors.add(createTestColor(colorId));
        Phone expectedPhone = createTestPhone(null, brand, model);
        expectedPhone.setColors(colors);

        phoneDao.save(expectedPhone);

        Optional<Phone> newPhone = phoneDao.get(4L);

        assertThat(newPhone).isPresent()
                .get()
                .matches(phone -> phone.getBrand().equals(brand))
                .matches(phone -> phone.getModel().equals(model))
                .matches(phone -> phone.getColors().equals(colors));
    }

    @Test
    public void shouldUpdatePhone() {
        Long phoneId = 1L;
        Long colorId = 1L;
        String brand = "update";
        String model = "update";
        Set<Color> colors = new HashSet<>();
        colors.add(createTestColor(colorId));
        Phone expectedPhone = createTestPhone(phoneId, brand, model);
        expectedPhone.setColors(colors);

        phoneDao.save(expectedPhone);

        Optional<Phone> updatedPhone = phoneDao.get(phoneId);

        assertThat(updatedPhone).isPresent()
                .get()
                .matches(phone -> phone.getBrand().equals(brand))
                .matches(phone -> phone.getModel().equals(model))
                .matches(phone -> phone.getColors().equals(colors));
    }

    private Phone createTestPhone(Long id, String brand, String model) {
        Phone phone = new Phone();

        phone.setId(id);
        phone.setBrand(brand);
        phone.setModel(model);

        return phone;
    }

    private Color createTestColor(Long id) {
        Color color = new Color();
        color.setId(id);
        return color;
    }
}
