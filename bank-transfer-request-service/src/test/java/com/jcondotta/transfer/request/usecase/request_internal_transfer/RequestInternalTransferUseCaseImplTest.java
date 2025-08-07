package com.jcondotta.transfer.request.usecase.request_internal_transfer;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.transfer.application.ports.output.messaging.request_internal_transfer.InternalTransferRequestedMessageProducer;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.RequestInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.mapper.RequestInternalTransferCommandMapper;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferFromAccountIdToIbanCommand;
import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.request.application.usecase.request_internal_transfer.RequestInternalTransferUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;

@ExtendWith(MockitoExtension.class)
class RequestInternalTransferUseCaseImplTest {

    @Mock
    private InternalTransferRequestedMessageProducer eventProducer;

    @Mock
    private RequestInternalTransferCommandMapper commandMapper;

    @Mock
    private RequestInternalTransferFromAccountIdToIbanCommand command;

    @Mock
    private InternalTransferRequestedEvent requestedEvent;

    private static final Clock FIXED_CLOCK_MADRID = TestClockExamples.FIXED_CLOCK_MADRID;

    private RequestInternalTransferUseCase useCase;

    @BeforeEach
    void setUp() {
//        useCase = new RequestInternalTransferUseCaseImpl(eventProducer, commandMapper, FIXED_CLOCK_MADRID);
    }

//    @Test
//    void shouldPublishInternalTransferEvent_whenCommandIsValid() {
//        when(commandMapper.toEvent(command, FIXED_CLOCK_MADRID)).thenReturn(requestedEvent);
//
//        useCase.execute(command);
//
//        verify(commandMapper).toEvent(command, FIXED_CLOCK_MADRID);
//        verify(eventProducer).publish(requestedEvent);
//        verifyNoMoreInteractions(commandMapper, eventProducer);
//    }
}
