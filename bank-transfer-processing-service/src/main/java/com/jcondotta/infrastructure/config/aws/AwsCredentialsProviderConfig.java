package com.jcondotta.infrastructure.config.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
public class AwsCredentialsProviderConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsCredentialsProviderConfig.class);

    @Bean
    @ConditionalOnProperty(name = { "aws.access-key-id", "aws.secret-key" })
    public AwsCredentialsProvider staticCredentialsProvider(
            @Value("${aws.access-key-id}") String accessKey,
            @Value("${aws.secret-key}")    String secretKey) {

        LOGGER.atInfo()
                .setMessage("Initializing AWS StaticCredentialsProvider with access key: {}")
                .addArgument(accessKey)
                .log();

        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey));
    }

    @Bean
    @ConditionalOnMissingBean(AwsCredentialsProvider.class)
    public AwsCredentialsProvider defaultCredentialsProvider() {
        LOGGER.info("Initializing AWS DefaultCredentialsProvider");

        return DefaultCredentialsProvider.builder().build();
    }
}
