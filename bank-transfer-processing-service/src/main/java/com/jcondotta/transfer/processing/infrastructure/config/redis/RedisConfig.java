package com.jcondotta.transfer.processing.infrastructure.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcondotta.transfer.application.ports.output.cache.CacheStore;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.cache.RedisCacheStore;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.BankAccountCdo;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public CacheStore<BankAccountCdo> bankAccountCache(
        RedisTemplate<String, BankAccountCdo> redisTemplate, RedissonClient redissonClient) {
        return new RedisCacheStore<>(redisTemplate, redissonClient, Duration.ofMinutes(15), BankAccountCdo.class);
    }

    @Bean
    public RedisTemplate<String, BankAccountCdo> bankAccountRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        return createTemplate(connectionFactory, objectMapper, BankAccountCdo.class);
    }

    private <T> RedisTemplate<String, T> createTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper, Class<T> valueType) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<T> valueSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, valueType);
        template.setValueSerializer(valueSerializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(valueSerializer);

        template.afterPropertiesSet();
        return template;
    }
}