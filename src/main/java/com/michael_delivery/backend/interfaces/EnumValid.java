package com.michael_delivery.backend.interfaces;
import com.michael_delivery.backend.util.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define the custom annotation
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)  // Specifies the validator class
public @interface EnumValid {

    String message() default "Invalid enum value";  // Default error message
    Class<?>[] groups() default {};  // Grouping for validation
    Class<? extends Payload>[] payload() default {};  // Additional data for the annotation
    Class<? extends Enum<?>> enumClass();  // Class type of the Enum being validated
}

