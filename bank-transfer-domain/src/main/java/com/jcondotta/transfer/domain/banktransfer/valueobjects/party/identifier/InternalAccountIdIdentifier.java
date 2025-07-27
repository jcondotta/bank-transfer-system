package com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;

import java.util.Objects;

public record InternalAccountIdIdentifier(BankAccountId bankAccountId) implements InternalPartyIdentifier {

    public InternalAccountIdIdentifier {
        Objects.requireNonNull(bankAccountId, BankAccountId.ID_NOT_NULL_MESSAGE);
    }

    @Override
    public InternalPartyIdentifierType type() {
        return InternalPartyIdentifierType.ACCOUNT_ID;
    }

    @Override
    public String value() {
        return bankAccountId.toString();
    }

    public static InternalAccountIdIdentifier of(BankAccountId bankAccountId) {
        return new InternalAccountIdIdentifier(bankAccountId);
    }
}
