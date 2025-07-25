package com.jcondotta.infrastructure.adapters.input.messaging.mapper;

import com.jcondotta.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.infrastructure.adapters.input.messaging.InternalTransferRequestedEventDTO;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.shared.valueobjects.Currency;
import com.jcondotta.usecase.process_internal_transfer.model.CreateInternalTransferCommand;
import com.jcondotta.usecase.process_internal_transfer.model.CreateInternalTransferFromAccountIdToIbanCommand;

import java.util.UUID;

public class InternalTransferRequestedEventMapper {

    public static InternalTransferRequestedEvent toDomain(InternalTransferRequestedEventDTO dto) {
        var sender = switch (dto.senderIdentifierType()) {
            case "ACCOUNT_ID" -> InternalAccountSender.of(UUID.fromString(dto.senderIdentifierValue()));
            case "IBAN" -> throw new UnsupportedOperationException("Sender iban is not yet implemented");
            default -> throw new IllegalStateException("Unexpected value: " + dto.senderIdentifierType());
        };

        var recipient = switch (dto.recipientIdentifierType()) {
            case "ACCOUNT_ID" -> InternalAccountRecipient.of(dto.recipientIdentifierValue());
            case "IBAN" -> InternalIbanRecipient.of(dto.recipientIdentifierValue());
            default -> throw new IllegalStateException("Unexpected value: " + dto.recipientIdentifierType());
        };

        return InternalTransferRequestedEvent.of(
            sender,
            recipient,
            MonetaryAmount.of(dto.amount(), Currency.valueOf(dto.currency())),
            dto.reference(),
            dto.requestedAt()
        );
    }

    public static CreateInternalTransferCommand toCommand(InternalTransferRequestedEvent event) {
        return new CreateInternalTransferFromAccountIdToIbanCommand(
            (InternalAccountSender) event.internalPartySender(),
            (InternalIbanRecipient) event.internalPartyRecipient(),
            event.monetaryAmount(),
            event.reference(),
            event.requestedAt()
        );
    }
}
