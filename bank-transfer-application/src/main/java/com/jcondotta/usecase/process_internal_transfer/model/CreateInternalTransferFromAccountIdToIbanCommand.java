package com.jcondotta.usecase.process_internal_transfer.model;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.bank_account.valueobject.Iban;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;

import java.time.ZonedDateTime;

public record CreateInternalTransferFromAccountIdToIbanCommand(
    InternalAccountSender partySender,
    InternalIbanRecipient partyRecipient,
    MonetaryAmount monetaryAmount,
    String reference,
    ZonedDateTime createdAt
) implements CreateInternalTransferCommand {

    public static CreateInternalTransferFromAccountIdToIbanCommand of(
        InternalAccountSender partySender,
        InternalIbanRecipient partyRecipient,
        MonetaryAmount monetaryAmount,
        String reference,
        ZonedDateTime createdAt) {

        return new CreateInternalTransferFromAccountIdToIbanCommand(partySender, partyRecipient, monetaryAmount, reference, createdAt);
    }

    public static CreateInternalTransferFromAccountIdToIbanCommand of(BankAccountId senderAccountId, Iban recipientiban, MonetaryAmount monetaryAmount, String reference, ZonedDateTime createdAt) {
        return of(InternalAccountSender.of(senderAccountId), InternalIbanRecipient.of(recipientiban), monetaryAmount, reference, createdAt);
    }
}
