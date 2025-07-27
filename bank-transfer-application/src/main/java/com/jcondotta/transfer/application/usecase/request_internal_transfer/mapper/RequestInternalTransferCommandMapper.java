package com.jcondotta.transfer.application.usecase.request_internal_transfer.mapper;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import org.mapstruct.Mapper;

import java.time.Clock;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface RequestInternalTransferCommandMapper {

    default InternalTransferRequestedEvent toEvent(RequestInternalTransferCommand command, Clock clock) {
        return InternalTransferRequestedEvent.of(
            command.partySender(),
            command.partyRecipient(),
            command.monetaryAmount(),
            command.reference(),
            ZonedDateTime.now(clock)
        );
    }
}
