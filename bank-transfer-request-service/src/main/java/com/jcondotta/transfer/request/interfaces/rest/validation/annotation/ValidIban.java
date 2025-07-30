package com.jcondotta.transfer.request.interfaces.rest.validation.annotation;

import com.jcondotta.transfer.request.interfaces.rest.validation.ValidationErrorMessages;
import com.jcondotta.transfer.request.interfaces.rest.validation.validator.IbanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IbanValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIban {
    String message() default ValidationErrorMessages.RECIPIENT_IBAN_NOT_VALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
