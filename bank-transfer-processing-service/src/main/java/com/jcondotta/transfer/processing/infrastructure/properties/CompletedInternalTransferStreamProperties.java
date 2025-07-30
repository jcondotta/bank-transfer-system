package com.jcondotta.transfer.processing.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "aws.kinesis.streams.internal-transfers.completed")
public record CompletedInternalTransferStreamProperties(String streamName) {}
