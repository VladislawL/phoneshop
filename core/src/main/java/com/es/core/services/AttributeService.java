package com.es.core.services;

import com.es.core.model.phone.Attribute;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AttributeService {

    private List<Attribute> attributes;

    @Value("#{${fields.map}}")
    private Map<String, String> attributeMap;

    @Value("#{'${fields.sorted}'.split(',')}")
    private List<String> sortFields;

    public AttributeService() {
        attributes = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        for (String key : attributeMap.keySet()) {
            Attribute attribute = new Attribute(attributeMap.get(key), key, sortFields.contains(key));
            attributes.add(attribute);
        }
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

}
