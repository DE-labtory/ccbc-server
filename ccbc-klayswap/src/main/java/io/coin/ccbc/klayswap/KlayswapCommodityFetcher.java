package io.coin.ccbc.klayswap;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Commodity;
import io.coin.ccbc.domain.Value;
import io.coin.ccbc.support.CacheProvider;
import io.coin.ccbc.support.CacheValueProvider;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;

public class KlayswapCommodityFetcher implements CacheProvider {

  private final KlayswapClient klayswapClient;
  private final KlayswapPoolRepository klayswapPoolRepository;
  private final KlayswapAprCalculator klayswapAprCalculator;

  public KlayswapCommodityFetcher(
      KlayswapClient klayswapClient,
      KlayswapPoolRepository klayswapPoolRepository,
      KlayswapAprCalculator klayswapAprCalculator
  ) {
    this.klayswapClient = klayswapClient;
    this.klayswapPoolRepository = klayswapPoolRepository;
    this.klayswapAprCalculator = klayswapAprCalculator;
  }

  @Cacheable(value = "all_commodity_klayswap")
  public List<Commodity> getAllCommodities() {
    return this.getAllCommoditiesRefreshFunction();
  }

  private List<Commodity> getAllCommoditiesRefreshFunction() {
    List<KlayswapPool> allKlayswapPools = new ArrayList<>(
        this.klayswapPoolRepository.getAllPoolMap().values()
    );
    Map<Address, PoolInfo> allPoolInfoMap = klayswapClient.getPoolInfos(
            allKlayswapPools
                .stream()
                .map(KlayswapPool::getAddress)
                .collect(Collectors.toList())
        ).stream()
        .collect(Collectors.toMap(
            PoolInfo::getPoolAddress,
            poolInfo -> poolInfo
        ));
    return allKlayswapPools.stream()
        .map(pool -> {
          Coin coin0 = pool.getCoin0();
          Coin coin1 = pool.getCoin1();
          PoolInfo poolInfo = allPoolInfoMap.get(pool.getAddress());
          Value coin0Value = Value.of(
              Amount.of(poolInfo.getToken0Reserve()),
              coin0.getPrice(),
              coin0.getDecimals()
          );
          Value coin1Value = Value.of(
              Amount.of(poolInfo.getToken1Reserve()),
              coin1.getPrice(),
              coin1.getDecimals()
          );
          return new Commodity(
              pool.getName(),
              pool.getAddress(),
              klayswapAprCalculator.calculate(
                  poolInfo,
                  coin0,
                  coin1
              ).doubleValue(),
              coin0Value.add(coin1Value).getValue().doubleValue(), // todo :: temp
              pool.getCoins()
          );
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<CacheInformation> getCacheInformation() {
    return List.of(
        new CacheInformation() {
          @Override
          public CacheValueProvider getCacheValueProvider() {
            return key -> getAllCommoditiesRefreshFunction();
          }

          @Override
          public String getCacheName() {
            return "all_commodity_klayswap";
          }

          @Override
          public Duration refreshTime() {
            return Duration.ofSeconds(60);
          }
        }
    );
  }
}
