package com.alura.hotel.util;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class CacheManagerUtil {
    private static final int MAXIMUM_SIZE = 100;
    private static final long EXPIRATION_MINUTES = 60;

    private final Cache<String, Object> cache;

    private CacheManagerUtil() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build();
    }

    private static final class InstanceHolder {
        private static final CacheManagerUtil instance = new CacheManagerUtil();
    }

    public static CacheManagerUtil getInstance() {
        return InstanceHolder.instance;
    }

    public void invalidateCache() {
        cache.invalidateAll();
        cache.cleanUp();
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.getIfPresent(key);
    }
}
