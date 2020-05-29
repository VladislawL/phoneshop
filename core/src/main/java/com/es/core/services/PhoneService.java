package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;

import java.util.List;

public interface PhoneService {
    List<Phone> getPhonePage(int page, String query, SortField sortField, SortOrder sortOrder);
    int getPagesCount(String searchQuery);
    Phone getPhoneById(long id);
}
