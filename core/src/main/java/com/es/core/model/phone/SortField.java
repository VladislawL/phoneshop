package com.es.core.model.phone;

public enum SortField {
    BRAND("brand"),
    MODEL("model"),
    DISPLAY_SIZE_INCHES("displaySizeInches"),
    PRICE("price"),
    DEFAULT("price");

    private String attribute;

    SortField(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
