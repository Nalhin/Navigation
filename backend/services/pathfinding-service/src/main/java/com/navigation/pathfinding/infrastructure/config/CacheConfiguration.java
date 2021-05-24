package com.navigation.pathfinding.infrastructure.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("graph", "graphBounded");
    }
}
