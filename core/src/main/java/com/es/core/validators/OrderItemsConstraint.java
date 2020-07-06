package com.es.core.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrderItemsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderItemsConstraint {
    String message() default "There were errors in the order items quantities";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
