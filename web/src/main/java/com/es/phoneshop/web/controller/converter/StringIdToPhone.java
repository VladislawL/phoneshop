package com.es.phoneshop.web.controller.converter;

import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringIdToPhone implements Converter<String, Phone> {

    @Autowired
    private PhoneService phoneService;

    @Override
    public Phone convert(String stringId) {
        return phoneService.getPhoneById(Long.parseLong(stringId));
    }
}
