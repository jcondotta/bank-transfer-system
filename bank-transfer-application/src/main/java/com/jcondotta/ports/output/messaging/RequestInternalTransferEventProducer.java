package com.jcondotta.ports.output.messaging;

import com.jcondotta.banktransfer.events.InternalTransferRequestedEvent;

public interface RequestInternalTransferEventProducer {

    void publish(InternalTransferRequestedEvent event);

}