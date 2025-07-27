package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record InternalTransferRequestedEventDTO (
    String senderIdentifierType,
    String senderIdentifierValue,
    String recipientIdentifierType,
    String recipientIdentifierValue,
    BigDecimal amount,
    String currency,
    String reference,
    ZonedDateTime requestedAt
) {}
