package com.jcondotta.transfer.domain.shared.events;

public interface DomainEvent<T> {
    EventId eventId();

    default String toStringEventId() {
        return eventId().value().toString();
    }
}
