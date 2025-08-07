package com.jcondotta.transfer.request.interfaces.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcondotta.transfer.application.ports.output.messaging.request_internal_transfer.InternalTransferRequestedMessageProducer;
import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.request.infrastructure.properties.RequestInternalTransferQueueProperties;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class SQSInternalTransferRequestedMessageProducer implements InternalTransferRequestedMessageProducer {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;

    private final InternalTransferRequestedMessageMapper messageMapper;
    private final RequestInternalTransferQueueProperties queueProperties;

    @Override
    @Timed(value = "sqs.producer.internal_transfer.requested", description = "Time to send internal transfer to SQS")
    public void send(InternalTransferRequestedEvent event) {
        try {
            var partitionKey = event.internalPartySender().identifier().value();
            var messageBody = objectMapper.writeValueAsString(messageMapper.toMessage(event));

            var sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueProperties.queueUrl())
                .messageBody(messageBody)
                .messageGroupId(DigestUtils.sha256Hex(partitionKey))
                .messageDeduplicationId(event.toStringEventId())
                .build();

            sqsAsyncClient.sendMessage(sendMessageRequest)
                .thenAccept(response ->
                    log.info("✅ Internal transfer message successfully sent to SQS FIFO [messageId={}]", response.messageId())
                )
                .exceptionally(ex -> {
                    log.error("❌ Failed to send internal transfer message to SQS FIFO", ex);
                    return null;
                });

        } catch (JsonProcessingException e) {
            log.error("❌ Failed to serialize internal transfer message to JSON", e);
        } catch (Exception e) {
            log.error("❌ Unexpected error while sending internal transfer message to SQS FIFO", e);
        }
    }
}