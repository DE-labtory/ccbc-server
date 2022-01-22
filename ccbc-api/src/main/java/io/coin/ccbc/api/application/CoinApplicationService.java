package io.coin.ccbc.api.application;

import io.coin.ccbc.api.application.dto.AddressAssetHistoryDto;
import io.coin.ccbc.api.application.dto.CoinPriceDto;
import io.coin.ccbc.api.application.dto.CreateCoinRequest;
import io.coin.ccbc.api.domain.AssetHistoryPeriod;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.HistoryTimeSeries;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.domain.PriceHistoryRepository;
import io.coin.ccbc.domain.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bomi
 * @date 2021/08/17
 */

@Service
public class CoinApplicationService {

  private final CoinRepository coinRepository;
  private final PriceHistoryRepository priceHistoryRepository;

  public CoinApplicationService(
      final CoinRepository coinRepository,
      final PriceHistoryRepository priceHistoryRepository
  ) {
    this.coinRepository = coinRepository;
    this.priceHistoryRepository = priceHistoryRepository;
  }

  @Transactional(readOnly = true)
  public List<CoinPriceDto> getAllCoins() {
    // TODO - query 한 번으로 해결!
    return this.coinRepository.findAllByOrderByOrderPriorityDescSymbolAsc().stream()
        .map(coin -> CoinPriceDto.of(
            coin,
            null
        ))
        .collect(Collectors.toList());
  }

  @Transactional
  public void createCoin(CreateCoinRequest request) {
    this.coinRepository.findByAddress(Address.of(request.getAddress())).ifPresent(
        coin -> {
          throw new IllegalArgumentException(
              String.format("%s is already exist", coin.getAddress().getValue())
          );
        });

    this.coinRepository.save(
        Coin.withoutPrice(
            Address.of(request.getAddress()),
            request.getSymbol(),
            request.getName(),
            request.getDecimals(),
            request.getOriginalSymbol(),
            request.getSymbolImageUrl()
        )
    );
  }

  @Transactional
  @Cacheable(value = "asset_history", key = "{#coinId, #period}")
  public List<AddressAssetHistoryDto> getCoinPriceHistory(String coinId,
      AssetHistoryPeriod period) {
    LocalDateTime now = Time.now();

    LocalDateTime openTime = period.getOpenTime(now);
    LocalDateTime closeTime = period.getCloseTime(now);
    List<LocalDateTime> intervalTimes = period.getIntervalTimes(openTime, closeTime);

    Coin coin = this.coinRepository.findByIdOrElseThrow(coinId);
    HistoryTimeSeries<PriceHistory> coinPriceHistories = new HistoryTimeSeries<>(
        this.priceHistoryRepository
            .findAllByCoinAndCollectedAtGreaterThanEqualAndCollectedAtLessThanEqual(
                coin, openTime, closeTime
            ));

    return intervalTimes.stream()
        .map(intervalTime -> {
          PriceHistory priceHistory = coinPriceHistories
              .getPreviousIfNotExist(intervalTime);
          if (priceHistory == null) {
            return null;
          }
          return new AddressAssetHistoryDto(intervalTime, priceHistory.getPrice().getValue());
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  public CoinPriceDto getCoin(String coinId) {
    LocalDateTime previousTime = Time.now().minusHours(24L);
    Coin coin = this.coinRepository.findByIdOrElseThrow(coinId);
    return CoinPriceDto.of(
        coin,
        null
    );
  }
}
