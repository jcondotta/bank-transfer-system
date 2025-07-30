package com.jcondotta.transfer.request.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "aws.kinesis.streams.request-internal-transfers")
public record RequestInternalTransferStreamProperties(String streamName) {}