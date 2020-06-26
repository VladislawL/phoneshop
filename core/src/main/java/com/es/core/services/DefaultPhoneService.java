package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.PhoneNotFoundException;
import com.es.core.model.phone.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultPhoneService implements PhoneService {

    @Resource
    private PhoneDao phoneDao;

    @Value("${phonesOnPage}")
    private int phonesOnPage;

    @Override
    public List<Phone> getPhonePage(int page, String query, String sortField, SortOrder sortOrder) {
        int offset = (page - 1) * phonesOnPage;
        return phoneDao.findOrderedPhoneListBySearchQuery(offset, phonesOnPage, query, sortField, sortOrder);
    }

    @Override
    public int getPagesCount(String searchQuery) {
        int phonesNumber = phoneDao.countPhonesWhereBrandAndModelLikeSearchQuery(searchQuery);

        if (phonesNumber % phonesOnPage == 0) {
            return phonesNumber / phonesOnPage;
        } else {
            return phonesNumber / phonesOnPage + 1;
        }
    }

    @Override
    public Phone getPhoneById(long id) {
        Optional<Phone> phone = phoneDao.get(id);
        if (phone.isPresent()) {
            return phone.get();
        } else {
            throw new PhoneNotFoundException(id);
        }
    }

    @Override
    public List<Phone> getPhonesById(List<Long> ids) {
        return phoneDao.getPhones(ids);
    }
}
