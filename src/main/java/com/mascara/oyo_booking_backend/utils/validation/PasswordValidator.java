package com.mascara.oyo_booking_backend.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/11/2023
 * Time      : 2:23 SA
 * Filename  : PasswordValidator
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W|\\_])(?=\\S+$).{8,20}$";

    @Override
    public void initialize(Password password) {
        ConstraintValidator.super.initialize(password);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null)
            return false;
        String password = input.trim();
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}