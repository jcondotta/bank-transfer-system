package com.jcondotta.transfer.application.ports.output.messaging.request_internal_transfer;

import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;

public interface InternalTransferRequestedMessageProducer {

    void send(InternalTransferRequestedEvent event);

}