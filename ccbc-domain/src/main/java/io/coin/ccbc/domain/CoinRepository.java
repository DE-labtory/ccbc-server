package io.coin.ccbc.domain;

import io.coin.ccbc.support.CacheProvider;
import io.coin.ccbc.support.CacheValueProvider;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String>, CacheProvider {

  Optional<Coin> findByAddress(Address address);

  Optional<Coin> findBySymbol(String symbol);

  Optional<Coin> findBySymbolIgnoreCase(String symbol);

  List<Coin> findAllByAddressIn(List<Address> addresses);

  List<Coin> findAllByOrderByOrderPriorityDescSymbolAsc();

  default Coin findBySymbolIgnoreCaseOrElseThrow(String symbol) {
    return this.findBySymbolIgnoreCase(symbol).orElseThrow(
        () -> new RuntimeException(String.format("coin with symbol '%s' does not exist", symbol))
    );
  }

  default Coin findByAddressOrElseThrow(Address address) {
    return this.findByAddress(address).orElseThrow(
        () -> new RuntimeException(String.format("coin with address '%s' does not exist", address))
    );
  }

  default Coin findBySymbolOrElseThrow(String symbol) {
    return this.findBySymbol(symbol).orElseThrow(
        () -> new RuntimeException(String.format("coin with symbol '%s' does not exist", symbol))
    );
  }

  default Coin findByIdOrElseThrow(String id) {
    return this.findById(id).orElseThrow(() -> new RuntimeException(
            String.format("coin with id '%s' does not exist", id)
        )
    );
  }

  @Cacheable("all_coin_map")
  default Map<Address, Coin> getAllCoinMap() {
    return this.getAllCoinMapRefreshFunction();
  }

  default Map<Address,Coin> getAllCoinMapRefreshFunction(){
    return findAll().stream().collect(Collectors.toMap(Coin::getAddress, coin -> coin));
  }

  default List<CacheInformation> getCacheInformation() {
    return List.of(
        new CacheInformation() {
          @Override
          public CacheValueProvider getCacheValueProvider() {
            return key->getAllCoinMapRefreshFunction();
          }

          @Override
          public String getCacheName() {
            return "all_coin_map";
          }

          @Override
          public Duration refreshTime() {
            return Duration.ofSeconds(15);
          }
        }
    );
  }
}
