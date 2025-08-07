package com.jcondotta.transfer.request.interfaces.messaging.producer;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.request.interfaces.messaging.model.InternalTransferRequestedMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class InternalTransferRequestedMessageMapper {

    public InternalTransferRequestedMessage toMessage(InternalTransferRequestedEvent event) {
        return new InternalTransferRequestedMessage(
            event.eventId().value(),
            event.internalPartySender().identifier().type().name(),
            event.internalPartySender().identifier().value(),
            event.internalPartyRecipient().identifier().type().name(),
            event.internalPartyRecipient().identifier().value(),
            event.monetaryAmount().amount().toPlainString(),
            event.monetaryAmount().currency().name(),
            event.reference(),
            event.requestedAt()
        );
    }
}
