package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.dao.PhoneDao;
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
    public List<Phone> getPhonePage(int page, String query, SortField sortField, SortOrder sortOrder) {
        int limit = 10;
        int offset = (page - 1 ) * limit;
        return phoneDao.findOrderedPhoneListBySearchQuery(offset, limit, query, sortOrder, sortField);
    }

    @Override
    public int getPagesCount(String searchQuery) {
        return phoneDao.countPhonesWhereBrandAndModelLikeSearchQuery(searchQuery) / 10;
    }

    @Override
    public Phone getPhoneById(long id) {
        return phoneDao.get(id).get();
    }
}
