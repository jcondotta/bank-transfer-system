package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.bank_account.valueobject.BankAccountId;

import java.util.Objects;
import java.util.UUID;

public record InternalAccountSender(BankAccountId bankAccountId) implements InternalPartySender {

    public InternalAccountSender {
        Objects.requireNonNull(bankAccountId, PartySender.SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    public static InternalAccountSender of(BankAccountId bankAccountId) {
        return new InternalAccountSender(bankAccountId);
    }

    public static InternalAccountSender of(UUID bankAccountId) {
        return new InternalAccountSender(BankAccountId.of(bankAccountId));
    }
}