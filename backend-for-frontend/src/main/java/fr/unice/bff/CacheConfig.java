package fr.unice.bff;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = Arrays.asList(
                new ConcurrentMapCache("menu"),
                new ConcurrentMapCache("categories"),
                new ConcurrentMapCache("subCategories"),
                new ConcurrentMapCache("items"),
                new ConcurrentMapCache("orders")
        );
        cacheManager.setCaches(caches);
        return cacheManager;

    }

}
