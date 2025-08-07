package com.jcondotta.transfer.application.ports.output.messaging.processing_internal_transfer;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferCompletedEvent;

public interface InternalTransferCompletedEventProducer {

    void publish(InternalTransferCompletedEvent event);
}