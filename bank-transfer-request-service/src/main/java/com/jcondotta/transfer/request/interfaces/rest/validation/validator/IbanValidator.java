package com.jcondotta.transfer.request.interfaces.rest.validation.validator;

import com.jcondotta.transfer.request.interfaces.rest.validation.annotation.ValidIban;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        String sanitized = value.replaceAll("\\s+", "").toUpperCase();
        return IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(sanitized);
    }
}
