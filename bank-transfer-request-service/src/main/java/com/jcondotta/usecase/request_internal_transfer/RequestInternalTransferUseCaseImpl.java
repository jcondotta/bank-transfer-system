package com.jcondotta.usecase.request_internal_transfer;

import com.jcondotta.ports.output.messaging.RequestInternalTransferEventProducer;
import com.jcondotta.usecase.request_internal_transfer.mapper.RequestInternalTransferCommandMapper;
import com.jcondotta.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@AllArgsConstructor
public class RequestInternalTransferUseCaseImpl implements RequestInternalTransferUseCase {

    private final RequestInternalTransferEventProducer eventProducer;
    private final RequestInternalTransferCommandMapper commandMapper;
    private final Clock clock;

    @Override
    public void execute(RequestInternalTransferCommand command) {
        var transferRequestedEvent = commandMapper.toEvent(command, clock);
        eventProducer.publish(transferRequestedEvent);
    }
}
