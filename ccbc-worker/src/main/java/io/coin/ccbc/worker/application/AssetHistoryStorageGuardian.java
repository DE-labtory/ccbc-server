package io.coin.ccbc.worker.application;

import io.coin.ccbc.domain.AssetHistoryRepository;
import io.coin.ccbc.support.alerter.Alerter;
import io.coin.ccbc.worker.domain.Worker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AssetHistoryStorageGuardian implements Worker {

  private final AssetHistoryRepository assetHistoryRepository;
  private final Alerter alerter;

  public AssetHistoryStorageGuardian(
      AssetHistoryRepository assetHistoryRepository,
      Alerter alerter
  ) {
    this.assetHistoryRepository = assetHistoryRepository;
    this.alerter = alerter;
  }

  @Override
  @Transactional
  public void work(LocalDateTime workingTime) {
    try {
      // day to week
      LocalDateTime start = workingTime.withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(7);
      LocalDateTime end = workingTime.withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(1);
      this.assetHistoryRepository.deleteAllByCollectedAtBetweenAndExceptOnTime(start, end);

      // week to month
      start = workingTime.withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(30);
      end = workingTime.withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(7);
      this.assetHistoryRepository.deleteAllByCollectedAtBetweenAndExceptForEveryFourHoursAtMidnight(start, end);

      // after month
      LocalDateTime time = workingTime.withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(30);
      this.assetHistoryRepository.deleteAllByCollectedAtBeforeAndExceptMidnight(time);

      AssetHistoryStorageGuardian.log
          .info(String.format("assetHistoryStorageGuardian finished '%s' time job", workingTime.format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    } catch (Exception e) {
      this.alerter.alert(
          "asset history storage guardian fail",
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
