package com.es.phoneshop.web.controller.converter;

import com.es.core.model.phone.SortOrder;
import org.springframework.core.convert.converter.Converter;

public class StringToSortOrder implements Converter<String, SortOrder> {

    @Override
    public SortOrder convert(String sortOrder) {
        try {
            return Enum.valueOf(SortOrder.class, sortOrder);
        } catch (IllegalArgumentException e) {
            return SortOrder.DESC;
        }
    }
}
