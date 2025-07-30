package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcondotta.transfer.application.ports.input.messaing.InternalTransferRequestedEventConsumer;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.ProcessInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.mapper.CreateInternalTransferCommandMapper;
import com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.mapper.InternalTransferRequestedEventMapper;
import com.jcondotta.transfer.processing.infrastructure.properties.RequestedInternalTransferStreamProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;
import software.amazon.awssdk.services.kinesis.model.Record;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KinesisInternalTransferRequestedEventConsumer implements InternalTransferRequestedEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisInternalTransferRequestedEventConsumer.class);

    private final KinesisClient kinesisClient;
    private final ProcessInternalTransferUseCase useCase;
    private final RequestedInternalTransferStreamProperties streamProperties;

    private final ObjectMapper objectMapper;
    private final CreateInternalTransferCommandMapper commandMapper;
    private final InternalTransferRequestedEventMapper eventMapper;

    private String shardIterator;

    @PostConstruct
    public void init() {
        String shardId = kinesisClient.describeStream(DescribeStreamRequest.builder()
                .streamName(streamProperties.streamName())
                .build())
            .streamDescription()
            .shards()
            .get(0)
            .shardId();

        GetShardIteratorResponse iteratorResponse = kinesisClient.getShardIterator(GetShardIteratorRequest.builder()
            .streamName(streamProperties.streamName())
            .shardId(shardId)
            .shardIteratorType(ShardIteratorType.TRIM_HORIZON) // ou TRIM_HORIZON se quiser todos os eventos
            .build());

        shardIterator = iteratorResponse.shardIterator();
        LOGGER.info("Initialized Kinesis shard iterator");
    }

    @Override
    @Scheduled(fixedDelay = 10000) // a cada 3 segundos
    public void consume() {
        if (shardIterator == null) return;

        GetRecordsResponse response = kinesisClient.getRecords(GetRecordsRequest.builder()
            .shardIterator(shardIterator)
            .limit(10)
            .build());

        List<Record> records = response.records();
        shardIterator = response.nextShardIterator();

        for (Record record : records) {
            ByteBuffer buffer = record.data().asByteBuffer();
            String message = StandardCharsets.UTF_8.decode(buffer).toString();
            LOGGER.info("📥 Received raw Kinesis event: {}", message);

            try {
                InternalTransferRequestedEventDTO requestedEventDTO = objectMapper.readValue(message, InternalTransferRequestedEventDTO.class);
                LOGGER.info("✅ Parsed event: {}", requestedEventDTO);

                var requestedDomainEvent = eventMapper.toDomain(requestedEventDTO);
                useCase.execute(commandMapper.toCommand(requestedDomainEvent));

                // Optionally execute use case:
                // useCase.execute(...);
            } catch (Exception e) {
                LOGGER.error("❌ Failed to parse event: {}", message, e);
            }
        }
    }
}
