package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging;

import java.time.ZonedDateTime;
import java.util.UUID;

public record InternalTransferRequestedMessage(
    UUID messageId,
    String senderIdentifierType,
    String senderIdentifierValue,
    String recipientIdentifierType,
    String recipientIdentifierValue,
    String amount,
    String currency,
    String reference,
    ZonedDateTime requestedAt
) {}