package com.jcondotta.transfer.application.ports.output.messaging;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferFailedEvent;

public interface InternalTransferFailedEventProducer {
    void publish(InternalTransferFailedEvent event);
}