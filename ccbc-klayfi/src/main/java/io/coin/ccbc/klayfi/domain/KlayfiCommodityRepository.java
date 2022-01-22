package io.coin.ccbc.klayfi.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.support.CacheProvider;
import io.coin.ccbc.support.CacheProvider.CacheInformation;
import io.coin.ccbc.support.CacheValueProvider;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KlayfiCommodityRepository extends JpaRepository<KlayfiCommodity, String>,
    CacheProvider {

  Optional<KlayfiCommodity> findByAddress(Address address);

  @Query(value = "SELECT k FROM KlayfiCommodity k LEFT JOIN FETCH k.pool LEFT JOIN FETCH k.coin")
  List<KlayfiCommodity> findAllWithPoolAndCoinRefreshFunction();

  @Cacheable("all_klayficommodity")
  default List<KlayfiCommodity> findAllWithPoolAndCoin() {
    return this.findAllWithPoolAndCoinRefreshFunction();
  }

  default List<CacheInformation> getCacheInformation() {
    return List.of(
        new CacheInformation() {
          @Override
          public CacheValueProvider getCacheValueProvider() {
            return key -> findAllWithPoolAndCoinRefreshFunction();
          }

          @Override
          public String getCacheName() {
            return "all_klayficommodity";
          }

          @Override
          public Duration refreshTime() {
            return Duration.ofSeconds(15);
          }
        }
    );
  }
}
