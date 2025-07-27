package com.jcondotta.transfer.application.usecase.request_internal_transfer.model;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;

public record RequestInternalTransferFromAccountIdToAccountIdCommand(
    InternalAccountSender partySender,
    InternalAccountRecipient partyRecipient,
    MonetaryAmount monetaryAmount,
    String reference

) implements RequestInternalTransferCommand {

    public static RequestInternalTransferFromAccountIdToAccountIdCommand of(InternalAccountSender partySender, InternalAccountRecipient partyRecipient, MonetaryAmount monetaryAmount, String reference) {
        return new RequestInternalTransferFromAccountIdToAccountIdCommand(partySender, partyRecipient, monetaryAmount, reference);
    }

    public static RequestInternalTransferFromAccountIdToAccountIdCommand of(BankAccountId senderAccountId, BankAccountId recipientAccountId, MonetaryAmount monetaryAmount, String reference) {
        return new RequestInternalTransferFromAccountIdToAccountIdCommand(InternalAccountSender.of(senderAccountId), InternalAccountRecipient.of(recipientAccountId), monetaryAmount, reference);
    }
}
