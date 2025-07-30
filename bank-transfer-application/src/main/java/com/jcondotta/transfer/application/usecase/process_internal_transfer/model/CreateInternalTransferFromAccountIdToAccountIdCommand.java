package com.jcondotta.transfer.application.usecase.process_internal_transfer.model;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;

import java.time.ZonedDateTime;

public record CreateInternalTransferFromAccountIdToAccountIdCommand(
    InternalAccountSender partySender,
    InternalAccountRecipient partyRecipient,
    MonetaryAmount monetaryAmount,
    String reference,
    ZonedDateTime createdAt
) implements CreateInternalTransferCommand {

    public static CreateInternalTransferFromAccountIdToAccountIdCommand of(
        InternalAccountSender partySender,
        InternalAccountRecipient partyRecipient,
        MonetaryAmount monetaryAmount,
        String reference,
        ZonedDateTime createdAt) {

        return new CreateInternalTransferFromAccountIdToAccountIdCommand(partySender, partyRecipient, monetaryAmount, reference, createdAt);
    }

    public static CreateInternalTransferFromAccountIdToAccountIdCommand of(BankAccountId senderAccountId, BankAccountId recipientAccountId, MonetaryAmount monetaryAmount, String reference, ZonedDateTime createdAt) {
        return of(InternalAccountSender.of(senderAccountId), InternalAccountRecipient.of(recipientAccountId), monetaryAmount, reference, createdAt);
    }
}
