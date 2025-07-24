package com.jcondotta.banktransfer.valueobjects;

import com.jcondotta.bank_account.valueobject.BankAccountId;

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
        return new InternalAccountRecipient(BankAccountId.of(bankAccountId));
    }
}