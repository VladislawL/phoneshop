package com.es.core.model.phone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcPhoneDaoIT extends AbstractDataBaseIT {

    @Autowired
    private JdbcPhoneDao phoneDao;

    @Before
    public void setup() throws IOException {
        fillDataBase("test-colors.sql");
    }

    @After
    public void clean() throws IOException {
        cleanDataBase();
    }

    @Test
    public void shouldFindLimitedPhoneList() throws IOException {
        fillDataBase("test-data.sql");
        int limit = 2;
        List<Phone> phones = phoneDao.findAll(0, limit);

        assertNotNull(phones);
        assertEquals(limit, phones.size());
        for (Phone phone: phones) {
            assertNotNull(phone.getColors());
        }
    }

    @Test
    public void shouldGetExitingPhoneById() throws IOException {
        fillDataBase("test-data.sql");
        Optional<Phone> phone = phoneDao.get(1L);

        assertTrue(phone.isPresent());
    }

    @Test
    public void shouldGetEmptyOptionalIfPhoneNotFound() {
        Optional<Phone> phone = phoneDao.get(1L);

        assertFalse(phone.isPresent());
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

        Optional<Phone> newPhone = phoneDao.get(1L);

        if (newPhone.isPresent()) {
            Phone phone = newPhone.get();
            Set<Color> updatedColors = phone.getColors();

            assertEquals(brand, phone.getBrand());
            assertEquals(model, phone.getModel());
            assertEquals(colors, updatedColors);
        } else {
            fail();
        }
    }

    @Test
    public void shouldUpdatePhone() throws IOException {
        fillDataBase("test-data.sql");
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

        if (updatedPhone.isPresent()) {
            Phone phone = updatedPhone.get();
            Set<Color> updatedColors = phone.getColors();

            assertEquals(brand, phone.getBrand());
            assertEquals(model, phone.getModel());
            assertEquals(colors, updatedColors);
        } else {
            fail();
        }
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
