package com.jcondotta.transfer.application.usecase.shared.model.idempotency;

import java.time.Instant;

public record IdempotencyRecord(String idempotencyKey, String requestHash, Instant createdAt) {}
