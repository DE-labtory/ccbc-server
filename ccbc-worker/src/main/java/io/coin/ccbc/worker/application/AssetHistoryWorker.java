package io.coin.ccbc.worker.application;

import io.coin.ccbc.domain.Account;
import io.coin.ccbc.support.alerter.Alerter;
import io.coin.ccbc.worker.domain.Worker;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import io.coin.ccbc.worker.domain.history.AssetHistoryProcessor;
import io.coin.ccbc.worker.domain.history.AssetHistoryReader;
import io.coin.ccbc.worker.domain.history.AssetHistoryWriter;
import io.coin.ccbc.worker.domain.history.AssetHistoryWritingItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssetHistoryWorker implements Worker, ApplicationRunner {

  private final AssetHistoryReader assetHistoryReader;
  private final AssetHistoryProcessor assetHistoryProcessor;
  private final AssetHistoryWriter assetHistoryWriter;
  private final Alerter alerter;

  public AssetHistoryWorker(
      AssetHistoryReader assetHistoryReader,
      AssetHistoryProcessor assetHistoryProcessor,
      AssetHistoryWriter assetHistoryWriter,
      Alerter alerter
  ) {
    this.assetHistoryReader = assetHistoryReader;
    this.assetHistoryProcessor = assetHistoryProcessor;
    this.assetHistoryWriter = assetHistoryWriter;
    this.alerter = alerter;
  }

  @Override
  public void work(LocalDateTime workingTime) {
    // TODO: 병렬처리
    try {
      WorkerExecutionContext context = new WorkerExecutionContext();
      context.put("workingTime", workingTime);
      List<Account> accounts = this.assetHistoryReader.read(context);
      List<AssetHistoryWritingItem> writingItems = this.assetHistoryProcessor
          .process(accounts, context);
      this.assetHistoryWriter.write(writingItems);
      AssetHistoryWorker.log
          .info(String.format("assetHistoryWorker finished '%s' time job", workingTime.format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    } catch (Exception e) {
      this.alerter.alert(
          "assetHistoryWorker worker fail",
          String.format("working time - %s\n"
                  + "error message - %s",
              workingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
              e.getMessage()),
          "warning"
      );
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    this.work(LocalDateTime.of(2021, 11, 23, 0, 0, 0, 0));
  }
}
