package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalAccountIdIdentifier;
import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalPartyIdentifier;

import java.util.Objects;
import java.util.UUID;

public record InternalAccountRecipient(BankAccountId bankAccountId) implements InternalPartyRecipient {

    public InternalAccountRecipient {
        Objects.requireNonNull(bankAccountId, PartyRecipient.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    public static InternalAccountRecipient of(BankAccountId bankAccountId) {
        return new InternalAccountRecipient(bankAccountId);
    }

    public static InternalAccountRecipient of(UUID bankAccountId) {
        return of(BankAccountId.of(bankAccountId));
    }

    public static InternalAccountRecipient of(String bankAccountId) {
        return of(UUID.fromString(bankAccountId));
    }

    @Override
    public InternalPartyIdentifier identifier() {
        return InternalAccountIdIdentifier.of(bankAccountId);
    }
}