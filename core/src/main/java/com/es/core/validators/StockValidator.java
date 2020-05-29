package com.es.core.validators;

import com.es.core.dao.StockDao;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StockValidator implements
        ConstraintValidator<QuantityConstraint, Object> {

    @Autowired
    private StockDao stockDao;

    private String phoneId;
    private String quantity;

    @Override
    public void initialize(QuantityConstraint constraintAnnotation) {
        this.phoneId = constraintAnnotation.phoneId();
        this.quantity = constraintAnnotation.quantity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        long phoneId = (long) beanWrapper.getPropertyValue(this.phoneId);
        long quantity = (long) beanWrapper.getPropertyValue(this.quantity);

        int stock = stockDao.getStock(phoneId);

        return quantity <= stock;
    }
}
