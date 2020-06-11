package com.es.core.services;

import java.util.List;

public interface PropertyService {
    String getProperty(String name);
    List<String> getAllProperties();
}
