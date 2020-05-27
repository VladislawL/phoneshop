package com.es.phoneshop.web.controller.converter;

import com.es.core.model.phone.SortField;
import org.springframework.core.convert.converter.Converter;

public class StringToSortFieldConverter implements Converter<String, SortField> {

    @Override
    public SortField convert(String sortField) {
        try {
            return Enum.valueOf(SortField.class, sortField);
        } catch (IllegalArgumentException e) {
            return SortField.DEFAULT;
        }
    }
}
