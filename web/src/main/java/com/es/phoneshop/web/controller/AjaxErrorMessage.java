package com.es.phoneshop.web.controller;

import java.util.HashMap;
import java.util.Map;

public class AjaxErrorMessage {

    private Map<String, Object> errorMessages;

    public AjaxErrorMessage() {
        errorMessages = new HashMap<>();
    }

    public Map<String, Object> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, Object> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
