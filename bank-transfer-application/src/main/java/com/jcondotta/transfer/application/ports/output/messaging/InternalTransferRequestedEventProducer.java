package com.jcondotta.transfer.application.ports.output.messaging;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;

public interface InternalTransferRequestedEventProducer {

    void publish(InternalTransferRequestedEvent event);

}