package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext-core-test.xml")
public class JdbcPhoneDaoTest {

    @Autowired
    private JdbcPhoneDao phoneDao;

    @Test
    public void testFindAll() {
        int limit = 2;
        List<Phone> phones = phoneDao.findAll(0, limit);

        assertNotNull(phones);
        assertEquals(limit, phones.size());
        for (Phone phone: phones) {
            assertNotNull(phone.getColors());
        }
    }

    @Test
    public void testGet() {
        Optional<Phone> phone = phoneDao.get(1L);

        if (!phone.isPresent()) {
            fail();
        }
    }

    @Test
    public void testSave() {
        String brand = "save";
        String model = "save";
        Long colorId = 1L;
        Set<Color> colors = new HashSet<>();
        colors.add(createTestColor(colorId));
        Phone expectedPhone = createTestPhone(null, brand, model);
        expectedPhone.setColors(colors);

        phoneDao.save(expectedPhone);

        Optional<Phone> newPhone = phoneDao.get(4L);

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
    public void testUpdate() {
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
