package com.es.core.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StockValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface QuantityConstraint {
    String message() default "Not enough stock";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String phoneId();

    String quantity();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        QuantityConstraint[] value();
    }
}
