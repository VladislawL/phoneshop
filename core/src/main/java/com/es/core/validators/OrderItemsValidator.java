package com.es.core.validators;

import com.es.core.model.order.OrderItem;
import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class OrderItemsValidator implements Validator {

    @Autowired
    private StockService stockService;

    private static final String INVALID_ORDER_ITEMS_VALIDATION_MESSAGE = "You're going to order as much phones as we don't have. Go back to cart to fix";

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderItem.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        List<OrderItem>  orderItems = (List<OrderItem>) o;
        for (OrderItem orderItem : orderItems) {
            if (stockService.getStock(orderItem.getPhone().getId()) < orderItem.getQuantity()) {
                errors.rejectValue("orderItems", "invalid.order.items", INVALID_ORDER_ITEMS_VALIDATION_MESSAGE);
                break;
            }
        }
    }

}
