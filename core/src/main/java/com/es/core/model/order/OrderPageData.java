package com.es.core.model.order;

import com.es.core.cart.CartItem;
import com.es.core.validators.PhoneNumber;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class OrderPageData {

    private List<CartItem> cartItems;

    @NotBlank(message = "{field.required}")
    private String firstName;

    @NotBlank(message = "{field.required}")
    private String lastName;

    @NotBlank(message = "{field.required}")
    private String deliveryAddress;

    @NotBlank(message = "{field.required}")
    @PhoneNumber(message = "{invalid.phone.number}")
    private String contactPhoneNo;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }
}
