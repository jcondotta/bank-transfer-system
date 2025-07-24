package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.bank_account.valueobject.Iban;

import java.util.Objects;

public record InternalIbanRecipient(Iban iban) implements InternalPartyRecipient {

    public InternalIbanRecipient {
        Objects.requireNonNull(iban, PartyRecipient.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    public static InternalIbanRecipient of(Iban iban) {
        return new InternalIbanRecipient(iban);
    }

    public static InternalIbanRecipient of(String value) {
        return new InternalIbanRecipient(Iban.of(value));
    }
}