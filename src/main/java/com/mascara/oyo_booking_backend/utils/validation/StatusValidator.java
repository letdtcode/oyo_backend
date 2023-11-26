package com.mascara.oyo_booking_backend.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/11/2023
 * Time      : 5:31 CH
 * Filename  : StatusValidator
 */
public class StatusValidator implements ConstraintValidator<Status, Object> {
    @Override
    public void initialize(Status status) {
        ConstraintValidator.super.initialize(status);
    }

    @Override
    public boolean isValid(Object input, ConstraintValidatorContext constraintValidatorContext) {
        if(input.toString().equals("ENABLE")|| input.toString().equals("DISABLE")) {
            return true;
        }
        return false;
    }
}
