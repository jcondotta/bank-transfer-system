package com.jcondotta.transfer.request.interfaces.messaging.model;

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