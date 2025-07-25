package com.jcondotta.messaging.producer;

import com.jcondotta.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.messaging.model.InternalTransferRequestedEventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternalTransferRequestedEventMapper {

    default InternalTransferRequestedEventDTO toDto(InternalTransferRequestedEvent event) {
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
