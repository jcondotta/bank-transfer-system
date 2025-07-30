package com.jcondotta.transfer.application.ports.output.messaging;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferCompletedEvent;

public interface InternalTransferCompletedEventProducer {
    void publish(InternalTransferCompletedEvent event);
}