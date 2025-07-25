package com.jcondotta.banktransfer.events;

import com.jcondotta.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.shared.events.DomainEvent;

import java.time.ZonedDateTime;

public record InternalTransferRequestedEvent(
    InternalPartySender internalPartySender,
    InternalPartyRecipient internalPartyRecipient,
    MonetaryAmount monetaryAmount,
    String reference,
    ZonedDateTime requestedAt) implements DomainEvent<InternalTransferRequestedEvent> {

    public static InternalTransferRequestedEvent of(InternalPartySender internalPartySender, InternalPartyRecipient internalPartyRecipient, MonetaryAmount monetaryAmount, String reference, ZonedDateTime requestedAt) {
        return new InternalTransferRequestedEvent(internalPartySender, internalPartyRecipient, monetaryAmount, reference, requestedAt);
    }
}