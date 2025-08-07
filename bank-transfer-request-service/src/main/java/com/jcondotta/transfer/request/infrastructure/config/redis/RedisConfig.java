package com.jcondotta.transfer.request.infrastructure.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcondotta.transfer.application.ports.output.cache.CacheStore;
import com.jcondotta.transfer.application.usecase.shared.model.idempotency.IdempotencyRecord;
import com.jcondotta.transfer.request.interfaces.cache.RedisCacheStore;
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
    public CacheStore<Object> idempotencyCacheStore(
        RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        return new RedisCacheStore<>(redisTemplate, redissonClient, Duration.ofMinutes(15), Object.class);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }
}