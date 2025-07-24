package com.jcondotta.bank_account.valueobject;

import java.util.Objects;
import java.util.UUID;

public record BankAccountId(UUID value) {

    public static final String ID_NOT_NULL_MESSAGE = "bankAccountId value must not be null.";

    public BankAccountId {
        Objects.requireNonNull(value, ID_NOT_NULL_MESSAGE);
    }

    public static BankAccountId of(UUID value) {
        return new BankAccountId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
