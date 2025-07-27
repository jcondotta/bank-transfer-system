package com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier;

import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;

import java.util.Objects;

public record InternalAccountIbanIdentifier(Iban iban) implements InternalPartyIdentifier {

    public InternalAccountIbanIdentifier {
        Objects.requireNonNull(iban, Iban.IBAN_NOT_NULL_MESSAGE);
    }

    @Override
    public InternalPartyIdentifierType type() {
        return InternalPartyIdentifierType.IBAN;
    }

    @Override
    public String value() {
        return iban.value();
    }

    public static InternalAccountIbanIdentifier of(Iban iban) {
        return new InternalAccountIbanIdentifier(iban);
    }
}
