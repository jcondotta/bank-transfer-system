package com.jcondotta.banktransfer.valueobjects.transfer_entry;

import com.jcondotta.banktransfer.valueobjects.PartyRecipient;
import com.jcondotta.banktransfer.valueobjects.PartySender;
import com.jcondotta.monetary_movement.enums.MovementType;
import com.jcondotta.monetary_movement.value_objects.MonetaryMovement;
import com.jcondotta.shared.valueobjects.Currency;

import java.math.BigDecimal;

public interface TransferEntry{

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