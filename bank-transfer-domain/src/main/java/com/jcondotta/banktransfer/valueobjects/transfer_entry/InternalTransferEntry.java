package com.jcondotta.banktransfer.valueobjects.transfer_entry;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.monetary_movement.enums.MovementType;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.monetary_movement.value_objects.MonetaryMovement;

import java.util.Objects;

public record InternalTransferEntry(InternalAccountSender partySender, InternalAccountRecipient partyRecipient, MonetaryMovement monetaryMovement)
    implements TransferEntry {

    public InternalTransferEntry {
        Objects.requireNonNull(partySender, SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
        Objects.requireNonNull(partyRecipient, RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
        Objects.requireNonNull(monetaryMovement, MONETARY_MOVEMENT_NOT_NULL_MESSAGE);
    }

    public static InternalTransferEntry of(BankAccountId senderAccountId, BankAccountId recipientAccountId, MovementType movementType, MonetaryAmount monetaryAmount) {
        return new InternalTransferEntry(
            InternalAccountSender.of(senderAccountId),
            InternalAccountRecipient.of(recipientAccountId),
            MonetaryMovement.of(movementType, monetaryAmount)
        );
    }

    public static InternalTransferEntry ofDebit(BankAccountId sourceAccountId, BankAccountId recipientAccountId, MonetaryAmount monetaryAmount) {
        return of(sourceAccountId, recipientAccountId, MovementType.DEBIT, monetaryAmount);
    }

    public static InternalTransferEntry ofCredit(BankAccountId sourceAccountId, BankAccountId recipientAccountId, MonetaryAmount monetaryAmount) {
        return of(sourceAccountId, recipientAccountId, MovementType.CREDIT, monetaryAmount);
    }
}
