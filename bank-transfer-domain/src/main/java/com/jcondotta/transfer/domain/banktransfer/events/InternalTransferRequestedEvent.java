package com.jcondotta.transfer.domain.banktransfer.events;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.events.DomainEvent;
import com.jcondotta.transfer.domain.shared.events.EventId;

import java.time.ZonedDateTime;
import java.util.UUID;

public record InternalTransferRequestedEvent(
    EventId eventId,
    InternalPartySender internalPartySender,
    InternalPartyRecipient internalPartyRecipient,
    MonetaryAmount monetaryAmount,
    String reference,
    ZonedDateTime requestedAt)
    implements DomainEvent<InternalTransferRequestedEvent> {

    public static InternalTransferRequestedEvent of(EventId eventId, InternalPartySender internalPartySender, InternalPartyRecipient internalPartyRecipient, MonetaryAmount monetaryAmount, String reference, ZonedDateTime requestedAt) {
        return new InternalTransferRequestedEvent(eventId, internalPartySender, internalPartyRecipient, monetaryAmount, reference, requestedAt);
    }
}