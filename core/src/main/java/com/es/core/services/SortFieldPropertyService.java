package com.es.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortFieldPropertyService implements PropertyService {

    @Value("#{'${fields.sorted}'.split(',')}")
    private List<String> sortFields;

    @Value("${fields.sorted.default}")
    private String defaultSortField;

    @Override
    public String getProperty(String name) {
        if (sortFields.contains(name)) {
            return name;
        } else {
            return defaultSortField;
        }
    }

    @Override
    public List<String> getAllProperties() {
        return sortFields;
    }

}
