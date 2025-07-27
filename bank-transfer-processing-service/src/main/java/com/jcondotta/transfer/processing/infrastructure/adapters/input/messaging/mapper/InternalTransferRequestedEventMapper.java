package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.mapper;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.*;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalPartyIdentifierType;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.InternalTransferRequestedEventDTO;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InternalTransferRequestedEventMapper {

    default InternalTransferRequestedEvent toDomain(InternalTransferRequestedEventDTO eventDTO) {
        var internalPartySender = mapInternalPartySender(eventDTO.senderIdentifierType(), eventDTO.senderIdentifierValue());
        var internalPartyRecipient = mapInternalPartyRecipient(eventDTO.recipientIdentifierType(), eventDTO.recipientIdentifierValue());
        var monetaryAmount = MonetaryAmount.of(eventDTO.amount(), Currency.valueOf(eventDTO.currency()));

        return InternalTransferRequestedEvent.of(
            internalPartySender, internalPartyRecipient, monetaryAmount, eventDTO.reference(), eventDTO.requestedAt()
        );
    }

    private InternalPartySender mapInternalPartySender(String identifierType, String identifierValue) {
        var internalPartyIdentifierType = InternalPartyIdentifierType.from(identifierType);

        return switch (internalPartyIdentifierType) {
            case ACCOUNT_ID -> InternalAccountSender.of(UUID.fromString(identifierValue));
            case IBAN -> throw new UnsupportedOperationException("Sender IBAN is not yet implemented");
        };
    }

    private InternalPartyRecipient mapInternalPartyRecipient(String identifierType, String identifierValue) {
        var internalPartyIdentifierType = InternalPartyIdentifierType.from(identifierType);

        return switch (internalPartyIdentifierType) {
            case ACCOUNT_ID -> InternalAccountRecipient.of(identifierValue);
            case IBAN -> InternalIbanRecipient.of(identifierValue);
        };
    }
}
