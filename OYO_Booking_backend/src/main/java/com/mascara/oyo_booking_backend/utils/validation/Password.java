package com.mascara.oyo_booking_backend.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/11/2023
 * Time      : 2:23 SA
 * Filename  : Password
 */
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Password invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
