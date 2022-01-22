package io.coin.ccbc.worker.application;

import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.support.alerter.Alerter;
import io.coin.ccbc.worker.domain.Worker;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import io.coin.ccbc.worker.domain.price.history.SyncPriceHIstoryReadingItem;
import io.coin.ccbc.worker.domain.price.history.SyncPriceHistoryProcessor;
import io.coin.ccbc.worker.domain.price.history.SyncPriceHistoryReader;
import io.coin.ccbc.worker.domain.price.history.SyncPriceHistoryWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceHistorySyncer implements Worker {

  private final SyncPriceHistoryReader syncPriceHistoryReader;
  private final SyncPriceHistoryProcessor syncPriceHistoryProcessor;
  private final SyncPriceHistoryWriter syncPriceHistoryWriter;
  private final Duration duration;
  private final Alerter alerter;

  public PriceHistorySyncer(
      SyncPriceHistoryReader syncPriceHistoryReader,
      SyncPriceHistoryProcessor syncPriceHistoryProcessor,
      SyncPriceHistoryWriter syncPriceHistoryWriter,
      @Qualifier("priceHistorySyncerDuration") Duration duration,
      Alerter alerter
  ) {
    this.syncPriceHistoryReader = syncPriceHistoryReader;
    this.syncPriceHistoryProcessor = syncPriceHistoryProcessor;
    this.syncPriceHistoryWriter = syncPriceHistoryWriter;
    this.duration = duration;
    this.alerter = alerter;
  }

  @Override
  public void work(LocalDateTime workingTime) {
    try {
      WorkerExecutionContext context = new WorkerExecutionContext();
      context.put("workingTime", workingTime);
      context.put("startTime", workingTime.minus(this.duration));
      List<SyncPriceHIstoryReadingItem> readingItems = this.syncPriceHistoryReader.read(context);
      List<PriceHistory> poolInfoHistories = this.syncPriceHistoryProcessor
          .process(readingItems, context);
      this.syncPriceHistoryWriter.write(poolInfoHistories);
      PriceHistorySyncer.log
          .info(String.format("priceHistorySyncer finished '%s' time job", workingTime.format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    } catch (Exception e) {
      this.alerter.alert(
          "price history syncer fail",
          String.format("start time - %s\n"
                  + "working time - %s\n"
                  + "error message - %s",
              workingTime.minus(this.duration)
                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
              workingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
              e.getMessage()),
          "warning"
      );
      log.error(e.getMessage(), e);
    }

  }
}
