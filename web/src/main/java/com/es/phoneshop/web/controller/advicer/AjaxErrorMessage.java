package com.es.phoneshop.web.controller.advicer;

public class AjaxErrorMessage {

    private String message;

    public AjaxErrorMessage() {
    }

    public AjaxErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
