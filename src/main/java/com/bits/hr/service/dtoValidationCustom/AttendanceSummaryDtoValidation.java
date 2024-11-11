package com.bits.hr.service.dtoValidationCustom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AttendanceSummaryDtoValidationCheck.class)
public @interface AttendanceSummaryDtoValidation {
    String message() default "{Invalid year, month , day}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}