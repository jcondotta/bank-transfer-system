package com.jcondotta.transfer.processing.infrastructure.adapters.output.messaging;

import com.jcondotta.transfer.application.ports.output.messaging.processing_internal_transfer.InternalTransferCompletedEventProducer;
import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KinesisInternalTransferCompletedEventProducer implements InternalTransferCompletedEventProducer {

    @Override
    public void publish(InternalTransferCompletedEvent event) {
        log.info("Internal transfer completed event published: {}", event);
    }
}
