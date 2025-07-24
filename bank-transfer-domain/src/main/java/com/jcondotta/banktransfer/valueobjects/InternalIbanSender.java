package com.jcondotta.banktransfer.valueobjects;

import com.jcondotta.bank_account.valueobject.Iban;

import java.util.Objects;

public record InternalIbanSender(Iban iban) implements InternalPartySender{

    public InternalIbanSender {
        Objects.requireNonNull(iban, PartySender.SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    public static InternalIbanSender of(Iban iban) {
        return new InternalIbanSender(iban);
    }

    public static InternalIbanSender of(String value) {
        return new InternalIbanSender(Iban.of(value));
    }
}