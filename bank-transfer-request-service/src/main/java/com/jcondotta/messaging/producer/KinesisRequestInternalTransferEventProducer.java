package com.jcondotta.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcondotta.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.ports.output.messaging.RequestInternalTransferEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class KinesisRequestInternalTransferEventProducer implements RequestInternalTransferEventProducer {

    private static final String STREAM_NAME = "request-internal-transfers";

    private final KinesisAsyncClient kinesisClient;
    private final ObjectMapper objectMapper;
    private final InternalTransferRequestedEventMapper mapper;

    @Override
    public void publish(InternalTransferRequestedEvent requestedEvent) {
        try {
            String json = objectMapper.writeValueAsString(mapper.toDto(requestedEvent));

            PutRecordRequest request = PutRecordRequest.builder()
                .streamName(STREAM_NAME)
                .partitionKey(requestedEvent.internalPartySender().identifier().value())
                .data(SdkBytes.fromString(json, StandardCharsets.UTF_8))
                .build();

            kinesisClient.putRecord(request)
                .thenAccept(response ->
                    log.info("Published internal transfer event to Kinesis: sequenceNumber={}, partitionKey={}",
                        response.sequenceNumber(),
                        request.partitionKey()
                    )
                )
                .exceptionally(ex -> {
                    log.error("Failed to publish internal transfer event to Kinesis", ex);
                    return null;
                });

        } catch (Exception e) {
            log.error("Error serializing internal transfer event", e);
        }
    }
}
