package com.jcondotta.bank_account.valueobject;

import java.util.Objects;

public record Iban(String value) {

    public static final String IBAN_VALUE_NOT_NULL_MESSAGE = "iban value must not be null.";

    public Iban {
        Objects.requireNonNull(value, IBAN_VALUE_NOT_NULL_MESSAGE);
    }

    public static Iban of(String value) {
        return new Iban(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean is(String otherIban) {
        return value.equals(otherIban);
    }
}
