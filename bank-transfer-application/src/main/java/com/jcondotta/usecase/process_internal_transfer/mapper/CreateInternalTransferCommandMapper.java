package com.jcondotta.usecase.process_internal_transfer.mapper;

import com.jcondotta.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.usecase.process_internal_transfer.model.CreateInternalTransferCommand;
import org.mapstruct.Mapper;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface CreateInternalTransferCommandMapper {

    default CreateInternalTransferCommand toCommand(InternalTransferRequestedEvent requestedEvent) {
        return new CreateInternalTransferCommand() {
            @Override
            public InternalPartySender partySender() {
                return requestedEvent.internalPartySender();
            }

            @Override
            public InternalPartyRecipient partyRecipient() {
                return requestedEvent.internalPartyRecipient();
            }

            @Override
            public MonetaryAmount monetaryAmount() {
                return requestedEvent.monetaryAmount();
            }

            @Override
            public String reference() {
                return requestedEvent.reference();
            }

            @Override
            public ZonedDateTime createdAt() {
                return requestedEvent.requestedAt();
            }
        };
    }
}
