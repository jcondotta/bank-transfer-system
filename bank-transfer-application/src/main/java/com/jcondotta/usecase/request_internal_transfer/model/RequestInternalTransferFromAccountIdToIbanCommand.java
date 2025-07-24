package com.jcondotta.usecase.request_internal_transfer.model;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.bank_account.valueobject.Iban;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;

public record RequestInternalTransferFromAccountIdToIbanCommand(
    InternalAccountSender partySender,
    InternalIbanRecipient partyRecipient,
    MonetaryAmount monetaryAmount,
    String reference

) implements RequestInternalTransferCommand {

    public RequestInternalTransferFromAccountIdToIbanCommand {
        if (partySender == null) {
            throw new NullPointerException("Sender account ID must not be null");
        }
        if (partyRecipient == null) {
            throw new NullPointerException("Recipient IBAN must not be null");
        }
        if (monetaryAmount == null) {
            throw new NullPointerException("Monetary amount must not be null");
        }
    }

    public static RequestInternalTransferFromAccountIdToIbanCommand of(InternalAccountSender partySender, InternalIbanRecipient partyRecipient, MonetaryAmount monetaryAmount, String reference) {
        return new RequestInternalTransferFromAccountIdToIbanCommand(partySender, partyRecipient, monetaryAmount, reference);
    }

    public static RequestInternalTransferFromAccountIdToIbanCommand of(BankAccountId senderAccountId, Iban recipientIban, MonetaryAmount monetaryAmount, String reference) {
        return new RequestInternalTransferFromAccountIdToIbanCommand(InternalAccountSender.of(senderAccountId), InternalIbanRecipient.of(recipientIban), monetaryAmount, reference);
    }
}
