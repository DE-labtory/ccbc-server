package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.HistoryTimeSeries;
import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.domain.PriceHistoryRepository;
import io.coin.ccbc.worker.domain.Reader;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SyncPriceHistoryReader implements Reader<SyncPriceHIstoryReadingItem> {

  private final PriceHistoryRepository priceHistoryRepository;

  public SyncPriceHistoryReader(PriceHistoryRepository priceHistoryRepository) {
    this.priceHistoryRepository = priceHistoryRepository;
  }

  @Override
  public List<SyncPriceHIstoryReadingItem> read(WorkerExecutionContext context) {
    LocalDateTime workingTime = (LocalDateTime) context.getOrThrow(
        "workingTime",
        () -> new IllegalArgumentException("workingTime does not exist")
    );
    LocalDateTime startTime = (LocalDateTime) context.getOrThrow(
        "startTime",
        () -> new IllegalArgumentException("startTime does not exist")
    );

    Map<Coin, List<PriceHistory>> priceHistories = this.priceHistoryRepository
        .findAllByCollectedAtGreaterThanEqualAndCollectedAtLessThan(
            startTime, workingTime)
        .stream()
        .collect(Collectors.groupingBy(PriceHistory::getCoin));

    return priceHistories.values()
        .stream()
        .map(histories ->
            histories
                .stream()
                .collect(Collectors.groupingBy(PriceHistory::getCoin))
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(Entry::getKey, e -> new HistoryTimeSeries<>(e.getValue()))
                )
        )
        // 범위 내에 데이터가 없을 시 가장 최신 내역을 첫 부분에 세팅해준다.
        .map(historyMap -> historyMap.entrySet()
            .stream()
            .map(timeSeriesEntry -> {
              Coin coin = timeSeriesEntry.getKey();
              HistoryTimeSeries<PriceHistory> timeSeries = timeSeriesEntry.getValue();
              if (timeSeries.isEmpty() || !startTime.isEqual(timeSeries.get(0).getCollectedAt())) {
                this.priceHistoryRepository.findTopByCoinAndCollectedAtBeforeOrderByCollectedAtDesc(
                    coin, startTime)
                    .ifPresent(
                        previousPriceHistory -> timeSeries.put(0, startTime, PriceHistory.collected(
                            previousPriceHistory.getCoin(),
                            startTime
                        )));
              }

              if (timeSeries.isEmpty()) {
                return null;
              }

              return timeSeries;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList()))
        .flatMap(List::stream)
        .map(SyncPriceHIstoryReadingItem::of)
        .collect(Collectors.toList());
  }
}
