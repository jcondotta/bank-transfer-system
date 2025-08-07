package com.jcondotta.transfer.domain.shared.events;

import java.util.Objects;
import java.util.UUID;

public record EventId(UUID value) {

    public static final String ID_NOT_NULL_MESSAGE = "event id value must not be null.";

    public EventId {
        Objects.requireNonNull(value, ID_NOT_NULL_MESSAGE);
    }

    public static EventId of(UUID value) {
        return new EventId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
