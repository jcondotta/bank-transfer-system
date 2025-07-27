package com.jcondotta.transfer.domain.banktransfer.valueobjects.transfer_entry;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartySender;
import com.jcondotta.transfer.domain.monetary_movement.enums.MovementType;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryMovement;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;

import java.math.BigDecimal;

public sealed interface TransferEntry permits InternalTransferEntry {

    String SENDER_IDENTIFIER_NOT_NULL_MESSAGE = "sender's identifier must not be null.";
    String RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE = "recipient's identifier must not be null.";
    String MONETARY_MOVEMENT_NOT_NULL_MESSAGE = "monetary movement must not be null.";

    PartySender partySender();
    PartyRecipient partyRecipient();
    MonetaryMovement monetaryMovement();

    default BigDecimal amount() {
        return monetaryMovement().amount();
    }

    default Currency currency() {
        return monetaryMovement().currency();
    }

    default MovementType movementType() {
        return monetaryMovement().movementType();
    }

    default boolean isDebit() {
        return monetaryMovement().isDebit();
    }

    default boolean isCredit() {
        return monetaryMovement().isCredit();
    }
}