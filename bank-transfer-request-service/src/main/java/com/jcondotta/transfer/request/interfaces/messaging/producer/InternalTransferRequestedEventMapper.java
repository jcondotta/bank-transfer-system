package com.jcondotta.transfer.request.interfaces.messaging.producer;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.request.interfaces.messaging.model.InternalTransferRequestedEventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternalTransferRequestedEventMapper {

    default InternalTransferRequestedEventDTO toEventDTO(InternalTransferRequestedEvent event) {
        return new InternalTransferRequestedEventDTO(
            event.internalPartySender().identifier().type().toString(),
            event.internalPartySender().identifier().value(),
            event.internalPartyRecipient().identifier().type().toString(),
            event.internalPartyRecipient().identifier().value(),
            event.monetaryAmount().amount(),
            event.monetaryAmount().currency().name(),
            event.reference(),
            event.requestedAt()
        );
    }
}
