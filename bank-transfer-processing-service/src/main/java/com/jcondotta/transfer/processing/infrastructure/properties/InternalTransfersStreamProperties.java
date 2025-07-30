package com.jcondotta.transfer.processing.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "aws.kinesis.streams.internal-transfers")
public record InternalTransfersStreamProperties(
    StreamProperties requested,
    StreamProperties failed,
    StreamProperties completed
) {
    public record StreamProperties(String streamName) {}
}
