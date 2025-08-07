package com.jcondotta.transfer.request.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.sqs.queues.request-internal-transfers")
public record RequestInternalTransferQueueProperties(String queueUrl) {}
