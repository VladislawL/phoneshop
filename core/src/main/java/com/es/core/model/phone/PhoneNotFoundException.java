package com.es.core.model.phone;

public class PhoneNotFoundException extends RuntimeException {

    private long phoneId;

    public PhoneNotFoundException(long phoneId) {
        super("Phone with id = " + phoneId + " not found");
        this.phoneId = phoneId;
    }

    public long getPhoneId() {
        return phoneId;
    }
}
