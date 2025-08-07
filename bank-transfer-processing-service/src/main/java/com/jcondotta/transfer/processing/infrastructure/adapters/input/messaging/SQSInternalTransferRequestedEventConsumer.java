package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcondotta.transfer.application.ports.input.messaging.request_internal_transfer.InternalTransferRequestedEventConsumer;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.ProcessInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.mapper.CreateInternalTransferCommandMapper;
import com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.mapper.InternalTransferRequestedEventMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SQSInternalTransferRequestedEventConsumer implements InternalTransferRequestedEventConsumer {

    private final ObjectMapper objectMapper;
    private final ProcessInternalTransferUseCase useCase;

    private final CreateInternalTransferCommandMapper commandMapper;
    private final InternalTransferRequestedEventMapper eventMapper;

    @SqsListener(value = "${cloud.aws.sqs.queues.request-internal-transfers.queue-name}")
    public void onMessage(String messageBody) {
        try {
            var transferRequestedMessage = objectMapper.readValue(messageBody, InternalTransferRequestedMessage.class);
            var requestedDomainEvent = eventMapper.toDomain(transferRequestedMessage);

            useCase.execute(commandMapper.toCommand(requestedDomainEvent));

//            log.info("✅ Transferência interna processada com sucesso. [sender={}, recipient={}]",
//                requestedDomainEvent.internalPartySender().identifier().value(),
//                requestedDomainEvent.internalPartyRecipient().identifier().value()
//            );
        } catch (Exception e) {
            log.error("❌ Erro ao processar mensagem SQS FIFO", e);
            // a mensagem será reenviada após o Visibility Timeout
            throw new RuntimeException("Erro no processamento da mensagem", e);
        }
    }

    @Override
    public void consume(String message) {

    }
}
