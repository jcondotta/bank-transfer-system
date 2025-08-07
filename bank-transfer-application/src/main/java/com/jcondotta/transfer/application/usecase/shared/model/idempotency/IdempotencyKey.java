package com.jcondotta.transfer.application.usecase.shared.model.idempotency;

import java.util.Objects;
import java.util.UUID;

public record IdempotencyKey(UUID value) {
    public IdempotencyKey {
        Objects.requireNonNull(value, "IdempotencyKey cannot be null");
    }

    public static IdempotencyKey of(UUID uuid) {
        return new IdempotencyKey(uuid);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
