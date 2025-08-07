package com.jcondotta.transfer.application.usecase.request_internal_transfer.mapper;

import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import com.jcondotta.transfer.application.usecase.shared.model.idempotency.IdempotencyKey;
import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.domain.shared.events.EventId;
import org.mapstruct.Mapper;

import java.time.Clock;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface RequestInternalTransferCommandMapper {

    default InternalTransferRequestedEvent toEvent(RequestInternalTransferCommand command, IdempotencyKey idempotencyKey, Clock clock) {
        return InternalTransferRequestedEvent.of(
            EventId.of(idempotencyKey.value()),
            command.partySender(),
            command.partyRecipient(),
            command.monetaryAmount(),
            command.reference(),
            ZonedDateTime.now(clock)
        );
    }
}
