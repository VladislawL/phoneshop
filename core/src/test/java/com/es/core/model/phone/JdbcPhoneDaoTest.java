package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

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
        String newBrand = "save";
        String newModel = "save";
        Phone expectedPhone = createTestPhone(null, newBrand, newModel);

        phoneDao.save(expectedPhone);

        Optional<Phone> newPhone = phoneDao.get(4L);

        if (newPhone.isPresent()) {
            Phone phone = newPhone.get();
            assertEquals(newBrand, phone.getBrand());
            assertEquals(newModel, phone.getModel());
        } else {
            fail();
        }
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        String updatedBrand = "update";
        String updatedModel = "update";
        Phone expectedPhone = createTestPhone(id, updatedBrand, updatedModel);

        phoneDao.save(expectedPhone);

        Optional<Phone> updatedPhone = phoneDao.get(id);

        if (updatedPhone.isPresent()) {
            Phone phone = updatedPhone.get();
            assertEquals(updatedBrand, phone.getBrand());
            assertEquals(updatedModel, phone.getModel());
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
}
