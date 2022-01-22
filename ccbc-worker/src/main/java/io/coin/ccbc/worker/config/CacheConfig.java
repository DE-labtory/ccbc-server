package io.coin.ccbc.worker.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.coin.ccbc.support.CacheProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CaffeineCache exchangePriceCaffeineCache() {
    return new CaffeineCache(
        "exchange_price_cache",
        Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build()
    );
  }

  @Bean
  public CacheManager cacheManager(
      List<CaffeineCache> caffeineCaches,
      List<CacheProvider> cacheProviders
  ) {
    List<Cache> caches = new ArrayList<>();
    cacheProviders.stream()
        .flatMap(cacheProvider -> cacheProvider.getCacheInformation().stream())
        .forEach(
            cacheInformation -> {
              CaffeineCache caffeineCache = new CaffeineCache(
                  cacheInformation.getCacheName(),
                  Caffeine.newBuilder()
                      .refreshAfterWrite(cacheInformation.refreshTime())
                      .build(key -> cacheInformation.getCacheValueProvider().load(key))
              );
              caches.add(caffeineCache);
            }
        );
    caches.addAll(caffeineCaches);
    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    simpleCacheManager.setCaches(caches);
    return simpleCacheManager;
  }

}
