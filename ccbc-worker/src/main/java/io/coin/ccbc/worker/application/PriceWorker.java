package io.coin.ccbc.worker.application;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.worker.domain.Worker;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import io.coin.ccbc.worker.domain.price.PriceProcessor;
import io.coin.ccbc.worker.domain.price.PriceReader;
import io.coin.ccbc.worker.domain.price.PriceWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceWorker implements Worker {

  private final PriceReader priceReader;
  private final PriceProcessor priceProcessor;
  private final PriceWriter priceWriter;

  public PriceWorker(
      PriceReader priceReader,
      PriceProcessor priceProcessor,
      PriceWriter priceWriter
  ) {
    this.priceReader = priceReader;
    this.priceProcessor = priceProcessor;
    this.priceWriter = priceWriter;
  }

  @Override
  public void work(LocalDateTime workingTime) {
    WorkerExecutionContext context = new WorkerExecutionContext();
    List<Coin> readingItems = this.priceReader.read(context);
    List<Coin> writingItems = this.priceProcessor.process(readingItems, context);
    this.priceWriter.write(writingItems);
    PriceWorker.log
        .info(String.format("priceWorker finished '%s' time job", workingTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
  }
}
