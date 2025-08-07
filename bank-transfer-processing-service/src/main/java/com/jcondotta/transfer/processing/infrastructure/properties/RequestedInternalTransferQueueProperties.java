package com.jcondotta.transfer.processing.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.sqs.queues.request-internal-transfers")
public record RequestedInternalTransferQueueProperties(String queueName) {}
