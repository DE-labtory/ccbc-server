package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.HistoryTimeSeries;
import io.coin.ccbc.domain.PriceHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SyncPriceHIstoryReadingItem {

  private final HistoryTimeSeries<PriceHistory> priceHistoryHistoryTimeSeries;

  public static SyncPriceHIstoryReadingItem of(
      final HistoryTimeSeries<PriceHistory> priceHistoryHistoryTimeSeries
  ) {
    return new SyncPriceHIstoryReadingItem(priceHistoryHistoryTimeSeries);
  }
}
