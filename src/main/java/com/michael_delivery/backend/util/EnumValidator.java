package com.michael_delivery.backend.util;
import com.michael_delivery.backend.interfaces.EnumValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        System.out.println("Enum Value: " + value);
        for (Enum<?> enumValue : enumValues) {
            if (enumValue == value) {
                return true;
            }
        }
        return false;
    }
}
