package com.jcondotta.transfer.request.application.usecase.request_internal_transfer;

import com.jcondotta.transfer.application.ports.output.messaging.InternalTransferRequestedEventProducer;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.RequestInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.mapper.RequestInternalTransferCommandMapper;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Slf4j
@Service
@AllArgsConstructor
public class RequestInternalTransferUseCaseImpl implements RequestInternalTransferUseCase {

    private final InternalTransferRequestedEventProducer eventProducer;
    private final RequestInternalTransferCommandMapper commandMapper;
    private final Clock clock;

    @Override
    public void execute(RequestInternalTransferCommand command) {
        log.info("Initiate internal transfer request between bank accounts");

        var transferRequestedEvent = commandMapper.toEvent(command, clock);

        log.debug("Publishing internal transfer requested event: {}", transferRequestedEvent);

        eventProducer.publish(transferRequestedEvent);
    }
}
