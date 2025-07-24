package com.jcondotta.banktransfer.valueobjects.transfer_entry;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.banktransfer.valueobjects.InternalAccountRecipient;
import com.jcondotta.banktransfer.valueobjects.InternalAccountSender;
import com.jcondotta.monetary_movement.enums.MovementType;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.monetary_movement.value_objects.MonetaryMovement;

public record InternalTransferEntryImpl(InternalAccountSender partySender, InternalAccountRecipient partyRecipient, MonetaryMovement monetaryMovement) implements InternalTransferEntry {

    public static InternalTransferEntryImpl of(BankAccountId senderAccountId, BankAccountId recipientAccountId, MovementType movementType, MonetaryAmount monetaryAmount) {
        return new InternalTransferEntryImpl(
            InternalAccountSender.of(senderAccountId),
            InternalAccountRecipient.of(recipientAccountId),
            MonetaryMovement.of(movementType, monetaryAmount)
        );
    }

    public static InternalTransferEntryImpl ofDebit(BankAccountId sourceAccountId, BankAccountId recipientAccountId, MonetaryAmount monetaryAmount) {
        return of(sourceAccountId, recipientAccountId, MovementType.DEBIT, monetaryAmount);
    }

    public static InternalTransferEntryImpl ofCredit(BankAccountId sourceAccountId, BankAccountId recipientAccountId, MonetaryAmount monetaryAmount) {
        return of(sourceAccountId, recipientAccountId, MovementType.CREDIT, monetaryAmount);
    }
}
