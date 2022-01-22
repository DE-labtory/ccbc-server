package io.coin.ccbc.api.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.AssetHistory;
import io.coin.ccbc.domain.AssetHistoryRepository;
import io.coin.ccbc.domain.HistoryTimeSeries;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AssetHistoryAssembler {

  private final AssetHistoryRepository assetHistoryRepository;

  public AssetHistoryAssembler(
      AssetHistoryRepository assetHistoryRepository
  ) {
    this.assetHistoryRepository = assetHistoryRepository;
  }

  @Cacheable(value = "asset_history", key = "{#address, #period}")
  public List<TotalAssetSnapshot> assembleTotalAssetHistory(
      Address address,
      LocalDateTime requestedTime,
      AssetHistoryPeriod period
  ) {
    LocalDateTime openTime = period.getOpenTime(requestedTime);
    LocalDateTime closeTime = period.getCloseTime(requestedTime);
    List<LocalDateTime> intervalTimes = period.getIntervalTimes(openTime, closeTime);

    HistoryTimeSeries<AssetHistory> assetHistoryHistoryTimeSeries = new HistoryTimeSeries<>(
        this.assetHistoryRepository.findAllByWalletAddressAndCollectedAtIn(address, intervalTimes)
    );

    return intervalTimes.stream()
        .map(intervalTime -> {
          AssetHistory assetHistory = assetHistoryHistoryTimeSeries
              .getPreviousIfNotExist(intervalTime);
          if (assetHistory == null) {
            return null;
          }
          return new TotalAssetSnapshot(intervalTime, assetHistory.getValue());
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
