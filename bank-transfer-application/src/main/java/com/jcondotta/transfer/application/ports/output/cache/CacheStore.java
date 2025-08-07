package com.jcondotta.transfer.application.ports.output.cache;

import java.util.Optional;
import java.util.function.Supplier;

public interface CacheStore<V> {

    void put(String cacheKey, V cacheValue);

    void putIfAbsent(String cacheKey, V cacheValue);

    Optional<V> computeIfAbsentWithLock(String cacheKey, Supplier<Optional<V>> mapping);

    Optional<V> getIfPresent(String cacheKey);

    Optional<V> getOrFetch(String cacheKey, Supplier<Optional<V>> supplier);

    default V get(String cacheKey) {
        return getIfPresent(cacheKey).orElse(null);
    }

}