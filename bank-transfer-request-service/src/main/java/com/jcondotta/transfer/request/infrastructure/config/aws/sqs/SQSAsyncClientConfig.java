package com.jcondotta.transfer.request.infrastructure.config.aws.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SQSAsyncClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSAsyncClientConfig.class);

    @Bean
    @ConditionalOnProperty(name = "cloud.aws.sqs.endpoint")
    public SqsAsyncClient sqsAsyncClientLocal(AwsCredentialsProvider credentialsProvider, Region region,
                                              @Value("${cloud.aws.sqs.endpoint}") String endpoint) {

        LOGGER.info("Initializing SQSAsyncClient with custom endpoint: {}", endpoint);

        return SqsAsyncClient.builder()
            .region(region)
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(credentialsProvider)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(SqsAsyncClient.class)
    public SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider credentialsProvider, Region region) {
        return SqsAsyncClient.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();
    }
}
