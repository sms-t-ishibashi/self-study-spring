package io.github.sms.t.ishibashi.sss.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {
    public static class CacheStoreMap<K, V> extends ConcurrentHashMap<K, V> {}

    @Bean
    public CacheStoreMap<String, Object> cacheStoreMap() {
        return new CacheStoreMap<>();
    }
}