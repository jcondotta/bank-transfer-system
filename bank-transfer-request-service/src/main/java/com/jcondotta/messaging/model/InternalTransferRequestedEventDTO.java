package com.jcondotta.messaging.model;

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
