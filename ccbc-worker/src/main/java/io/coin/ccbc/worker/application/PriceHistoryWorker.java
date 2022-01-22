package io.coin.ccbc.worker.application;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.support.alerter.Alerter;
import io.coin.ccbc.worker.domain.Worker;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import io.coin.ccbc.worker.domain.price.history.PriceHistoryProcessor;
import io.coin.ccbc.worker.domain.price.history.PriceHistoryReader;
import io.coin.ccbc.worker.domain.price.history.PriceHistoryWriter;
import io.coin.ccbc.worker.domain.price.history.PriceHistoryWritingItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Bomi
 * @date 2021/07/18
 */

@Service
@Slf4j
public class PriceHistoryWorker implements Worker {

  private final PriceHistoryReader priceHistoryReader;
  private final PriceHistoryProcessor priceHistoryProcessor;
  private final PriceHistoryWriter priceHistoryWriter;
  private final Alerter alerter;

  public PriceHistoryWorker(
      final PriceHistoryReader priceHistoryReader,
      final PriceHistoryProcessor priceHistoryProcessor,
      final PriceHistoryWriter priceHistoryWriter,
      Alerter alerter
  ) {
    this.priceHistoryReader = priceHistoryReader;
    this.priceHistoryProcessor = priceHistoryProcessor;
    this.priceHistoryWriter = priceHistoryWriter;
    this.alerter = alerter;
  }

  @Override
  public void work(LocalDateTime workingTime) {
    try {
      WorkerExecutionContext context = new WorkerExecutionContext();
      context.put("workingTime", workingTime);
      List<Coin> readingItems = this.priceHistoryReader.read(context);
      List<PriceHistoryWritingItem> writingItems = this.priceHistoryProcessor
          .process(readingItems, context);
      this.priceHistoryWriter.write(writingItems);
      PriceHistoryWorker.log
          .info(String.format("priceHistoryWorker finished '%s' time job", workingTime.format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    } catch (Exception e) {
      this.alerter.alert(
          "price history worker fail",
          String.format("working time - %s\n"
                  + "error message - %s",
              workingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
              e.getMessage()),
          "warning"
      );
      log.error(e.getMessage(), e);
    }

  }
}
