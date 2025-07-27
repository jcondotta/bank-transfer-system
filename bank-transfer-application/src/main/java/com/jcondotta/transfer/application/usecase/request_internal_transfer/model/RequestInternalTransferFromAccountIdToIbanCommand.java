package com.jcondotta.transfer.application.usecase.request_internal_transfer.model;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;

public record RequestInternalTransferFromAccountIdToIbanCommand(
    InternalAccountSender partySender,
    InternalIbanRecipient partyRecipient,
    MonetaryAmount monetaryAmount,
    String reference

) implements RequestInternalTransferCommand {

    public static RequestInternalTransferFromAccountIdToIbanCommand of(InternalAccountSender partySender, InternalIbanRecipient partyRecipient, MonetaryAmount monetaryAmount, String reference) {
        return new RequestInternalTransferFromAccountIdToIbanCommand(partySender, partyRecipient, monetaryAmount, reference);
    }

    public static RequestInternalTransferFromAccountIdToIbanCommand of(BankAccountId senderAccountId, Iban recipientIban, MonetaryAmount monetaryAmount, String reference) {
        return new RequestInternalTransferFromAccountIdToIbanCommand(InternalAccountSender.of(senderAccountId), InternalIbanRecipient.of(recipientIban), monetaryAmount, reference);
    }
}
