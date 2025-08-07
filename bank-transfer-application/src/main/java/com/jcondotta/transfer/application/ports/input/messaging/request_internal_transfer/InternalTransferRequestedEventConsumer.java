package com.jcondotta.transfer.application.ports.input.messaging.request_internal_transfer;

public interface InternalTransferRequestedEventConsumer {

    void consume(String message);
}