package com.jcondotta.transfer.processing.infrastructure.adapters.output.cache;

import com.jcondotta.transfer.application.ports.output.cache.CacheStore;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class RedisCacheStore<V> implements CacheStore<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheStore.class);

    private final RedisTemplate<String, V> redisTemplate;
    private final RedissonClient redissonClient;
    private final Duration defaultTimeToLive;
    private final Class<V> valueClass;

    public RedisCacheStore(RedisTemplate<String, V> redisTemplate, RedissonClient redissonClient, Duration defaultTimeToLive, Class<V> valueClass) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
        this.defaultTimeToLive = defaultTimeToLive;
        this.valueClass = valueClass;
    }

    @Override
    public void put(String cacheKey, V cacheValue) {
        LOGGER.debug("Adding cache entry: key='{}', value='{}'", cacheKey, cacheValue);

        redisTemplate
            .opsForValue()
            .set(cacheKey, cacheValue, defaultTimeToLive);

        LOGGER.info("Cache put: key='{}' successfully stored.", cacheKey);
    }

    @Override
    public void putIfAbsent(String cacheKey, V cacheValue) {
        LOGGER.debug("Attempting to set cache entry for key='{}' if absent.", cacheKey);

        Boolean wasEntrySet = redisTemplate
            .opsForValue()
            .setIfAbsent(cacheKey, cacheValue, defaultTimeToLive.getSeconds(), TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(wasEntrySet)) {
            LOGGER.info("Cache entry set for key='{}' as it was absent.", cacheKey);
        }
        else {
            LOGGER.info("Cache entry for key='{}' already existed; value was not updated.", cacheKey);
        }
    }

    @Override
    public Optional<V> computeIfAbsentWithLock(String key, Supplier<Optional<V>> mapping) {
        return Optional.empty();
    }

    @Override
    public Optional<V> getIfPresent(String cacheKey) {
        V value = redisTemplate.opsForValue().get(cacheKey);
        if (value != null) {
            LOGGER.debug("Cache hit: key='{}'", cacheKey);
            if (valueClass.isInstance(value)) {
                return Optional.of(valueClass.cast(value));
            }
            else {
                LOGGER.warn("Cache key='{}' has unexpected type: {}", cacheKey, value.getClass().getName());
                return Optional.empty();
            }
        }

        LOGGER.info("Cache miss: key='{}'", cacheKey);
        return Optional.empty();
    }

    @Override
    public Optional<V> getOrFetch(String cacheKey, Supplier<Optional<V>> supplier) {
        var cachedValue = getIfPresent(cacheKey);
        if (cachedValue.isPresent()) return cachedValue;

        var acquired = false;
        var lock = redissonClient.getLock(cacheKey + ":lock");
        try {
            acquired = lock.tryLock(0, 10, TimeUnit.SECONDS);
            if (acquired) {
                Optional<V> result = supplier.get();
                result.ifPresent(value -> put(cacheKey, value));
                return result;
            }

            acquired = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!acquired) return Optional.empty();

            return getIfPresent(cacheKey);
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
        finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
