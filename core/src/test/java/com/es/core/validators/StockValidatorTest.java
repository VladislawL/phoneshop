package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.dao.StockDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext-core.xml")
public class StockValidatorTest {

    @Autowired
    private StockDao stockDao;

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void shouldReturnTrueIfQuantityIsLessThanStock() {
        StockValidator stockValidator = new StockValidator();
        stockValidator.setStockDao(stockDao);
        stockValidator.initialize(new StockValidatorTestClass());
        CartItem cartItem = new CartItem(1, 1);
        assertThat(stockValidator.isValid(cartItem, constraintValidatorContext)).isTrue();
    }

    @Test
    public void shouldReturnFalseIfQuantityIsGreaterThanStock() {
        StockValidator stockValidator = new StockValidator();
        stockValidator.setStockDao(stockDao);
        stockValidator.initialize(new StockValidatorTestClass());
        CartItem cartItem = new CartItem(1, 10);
        assertThat(stockValidator.isValid(cartItem, constraintValidatorContext)).isFalse();
    }

    private class StockValidatorTestClass implements QuantityConstraint {
        @Override
        public String message() {
            return "is not valid";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public String phoneId() {
            return "phoneId";
        }

        @Override
        public String quantity() {
            return "quantity";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }
}
