package com.jcondotta.ports.output.messaging;

//public interface DomainEventProducer<T extends DomainEvent> {
public interface DomainEventProducer<T extends Object> {
    void publish(T event);
}