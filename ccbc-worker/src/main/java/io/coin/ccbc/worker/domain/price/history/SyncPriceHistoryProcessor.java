package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.HistoryTimeSeries;
import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.worker.domain.Processor;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SyncPriceHistoryProcessor implements
    Processor<SyncPriceHIstoryReadingItem, PriceHistory> {

  private final Duration interval;

  public SyncPriceHistoryProcessor(@Qualifier("priceHistorySyncerInterval") Duration interval) {
    this.interval = interval;
  }

  @Override
  public List<PriceHistory> process(List<SyncPriceHIstoryReadingItem> items,
      WorkerExecutionContext context) {
    LocalDateTime workingTime = (LocalDateTime) context.getOrThrow(
        "workingTime",
        () -> new IllegalArgumentException("workingTime does not exist")
    );
    LocalDateTime startTime = (LocalDateTime) context.getOrThrow(
        "startTime",
        () -> new IllegalArgumentException("startTime does not exist")
    );

    return items.stream()
        .map(item -> this.process(item, startTime, workingTime, interval))
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private List<PriceHistory> process(
      SyncPriceHIstoryReadingItem item,
      LocalDateTime startTime,
      LocalDateTime workingTime,
      Duration interval
  ) {
    HistoryTimeSeries<PriceHistory> priceHistoryTimeSeries = item
        .getPriceHistoryHistoryTimeSeries();

    List<PriceHistory> missingPriceHistory = new ArrayList<>();
    for (LocalDateTime time = startTime;
        time.isEqual(workingTime) || time.isBefore(workingTime);
        time = time.plus(interval)) {
      PriceHistory priceHistory = priceHistoryTimeSeries.get(time);
      if (priceHistory != null) {
        continue;
      }
      PriceHistory previousPriceHistory = priceHistoryTimeSeries.getPreviousIfNotExist(time);
      if (previousPriceHistory == null) {
        continue;
      }
      missingPriceHistory.add(PriceHistory.collected(
          previousPriceHistory.getCoin(),
          time
      ));
    }
    return missingPriceHistory;
  }
}
