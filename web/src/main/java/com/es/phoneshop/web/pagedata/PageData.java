package com.es.phoneshop.web.pagedata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PageData {

    @Value("#{propertySource['fields.sorted']}")
    private List<String> sortFields;

    public List<String> getSortedFields() {
        return sortFields;
    }
}
