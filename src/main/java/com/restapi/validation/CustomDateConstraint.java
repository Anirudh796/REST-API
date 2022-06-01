package com.restapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD})
@Constraint(validatedBy = CustomDateValidator.class)
public @interface CustomDateConstraint {
	String message() default "Invalid Phone number";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
