package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.mapper;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.*;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalPartyIdentifierType;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.events.EventId;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.InternalTransferRequestedMessage;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InternalTransferRequestedEventMapper {

    default InternalTransferRequestedEvent toDomain(InternalTransferRequestedMessage message) {
        var internalPartySender = mapInternalPartySender(message.senderIdentifierType(), message.senderIdentifierValue());
        var internalPartyRecipient = mapInternalPartyRecipient(message.recipientIdentifierType(), message.recipientIdentifierValue());
        var monetaryAmount = MonetaryAmount.of(new BigDecimal(message.amount()), Currency.valueOf(message.currency()));

        return InternalTransferRequestedEvent.of(
            EventId.of(message.messageId()), internalPartySender, internalPartyRecipient, monetaryAmount, message.reference(), message.requestedAt()
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
