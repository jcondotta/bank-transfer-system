package com.jcondotta.transfer.application.ports.output.messaging;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;

public interface RequestInternalTransferEventProducer {

    void publish(InternalTransferRequestedEvent event);

}