package com.es.core.validators;

import com.es.core.dao.StockDao;
import com.es.core.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class OrderItemsValidator implements ConstraintValidator<OrderItemsConstraint, List<OrderItem>> {

    @Autowired
    private StockDao stockDao;

    @Override
    public boolean isValid(List<OrderItem> orderItems, ConstraintValidatorContext context) {
        for (OrderItem orderItem : orderItems) {
            if (!stockDao.checkStock(orderItem.getPhone().getId(), orderItem.getQuantity())) {
                return false;
            }
        }
        return true;
    }

}
