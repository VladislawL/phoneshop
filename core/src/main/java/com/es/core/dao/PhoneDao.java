package com.es.core.dao;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortOrder;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> findOrderedPhoneListBySearchQuery(int offset, int limit, String searchQuery, String sortField, SortOrder sortOrder);
    int countPhonesWhereBrandAndModelLikeSearchQuery(String searchQuery);
}
