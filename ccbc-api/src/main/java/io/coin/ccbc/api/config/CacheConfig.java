package io.coin.ccbc.api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import io.coin.ccbc.api.domain.AssetHistoryPeriod;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Time;
import io.coin.ccbc.klayswap.KlayswapPoolRepository;
import io.coin.ccbc.support.CacheProvider;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

  @Bean
  public CaffeineCache assetHistoryCaffeineCache() {
    return new CaffeineCache("asset_history", Caffeine.newBuilder()
        .expireAfter(new Expiry<>() {
          @Override
          public long expireAfterCreate(@NonNull Object key, @NonNull Object value,
              long currentTime) {
            return this.resolveAssetHistoryIntervalNanoTime(key);
          }

          @Override
          public long expireAfterUpdate(@NonNull Object key, @NonNull Object value,
              long currentTime,
              @NonNegative long currentDuration) {
            return this.resolveAssetHistoryIntervalNanoTime(key);
          }

          @Override
          public long expireAfterRead(@NonNull Object key, @NonNull Object value, long currentTime,
              @NonNegative long currentDuration) {
            return currentDuration;
          }

          private long resolveAssetHistoryIntervalNanoTime(@NonNull Object key) {
            if (!(key instanceof List)) {
              throw new IllegalStateException("assetHistory cache should has multiple keys");
            }
            List<Object> keys = (List<Object>) key;

            if (!(keys.get(1) instanceof AssetHistoryPeriod)) {
              throw new IllegalStateException(
                  "assetHistory cache has invalid key: second key should be AssetHistoryPeriod");
            }

            AssetHistoryPeriod period = (AssetHistoryPeriod) keys.get(1);
            LocalDateTime now = Time.now();
            // minus 1 minutes to support worker buffer
            return Duration.between(
                now,
                period.getCloseTime(now).plus(period.getInterval())
            ).minusMinutes(3).toNanos();
          }
        })
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
