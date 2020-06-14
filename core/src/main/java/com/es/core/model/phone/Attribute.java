package com.es.core.model.phone;

public class Attribute {
    private String caption;
    private String name;
    private boolean sorted;

    public Attribute() {
    }

    public Attribute(String caption, String name, boolean isSorted) {
        this.caption = caption;
        this.name = name;
        this.sorted = isSorted;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        sorted = sorted;
    }
}
