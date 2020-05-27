package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DefaultPhoneService implements PhoneService {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> getPhonePage(int page, SortField sortField, SortOrder sortOrder) {
        int limit = 10;
        int offset = (page - 1 ) * limit;
        return phoneDao.findAll(offset, limit, sortOrder, sortField);
    }

    @Override
    public Phone findPhoneByBrandAndModel(String brand, String model) {
        return null;
    }
}
