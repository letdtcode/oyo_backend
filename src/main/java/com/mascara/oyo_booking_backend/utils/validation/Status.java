package com.mascara.oyo_booking_backend.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/11/2023
 * Time      : 5:10 CH
 * Filename  : Status
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
@Documented
public @interface Status {
    String message() default "Status must be ENABLE or DISABLE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
