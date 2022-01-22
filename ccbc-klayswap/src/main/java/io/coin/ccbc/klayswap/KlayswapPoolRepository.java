package io.coin.ccbc.klayswap;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.support.CacheProvider;
import io.coin.ccbc.support.CacheValueProvider;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlayswapPoolRepository extends JpaRepository<KlayswapPool, String>, CacheProvider {

  Optional<KlayswapPool> findByAddress(Address address);

  default KlayswapPool findByAddressOrElseThrow(Address address) {

    return this.findByAddress(address).orElseThrow(() -> new IllegalArgumentException(
            String.format("pool with address '%s' does not exist", address.getValue())
        )
    );
  }

  default KlayswapPool findByIdOrElseThrow(String id) {
    return this.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("pool with id '%s' does not exist", id)
        ));
  }

  @Cacheable(value = "all_pool_map")
  default Map<Address, KlayswapPool> getAllPoolMap() {
    return getCacheableAllPoolMap();
  }

  default Map<Address, KlayswapPool> getCacheableAllPoolMap() {
    return findAll()
        .stream()
        .collect(Collectors.toMap(KlayswapPool::getAddress, pool -> pool));
  }

  default List<CacheInformation> getCacheInformation() {
    return List.of(new CacheInformation() {
      @Override
      public CacheValueProvider getCacheValueProvider() {
        return key->getCacheableAllPoolMap();
      }

      @Override
      public String getCacheName() {
        return "all_pool_map";
      }

      @Override
      public Duration refreshTime() {
        return Duration.ofMinutes(5);
      }
    });
  }
}
