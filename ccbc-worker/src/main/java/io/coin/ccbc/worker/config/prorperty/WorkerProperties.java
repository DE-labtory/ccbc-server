package io.coin.ccbc.worker.config.prorperty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bomi
 * @date 2021/08/04
 */

@Getter
@Setter
@Configuration
public class WorkerProperties {

  @Value("${app.price-worker.cron}")
  private String priceWorkerCron;

  @Value("${app.asset-history-worker.cron}")
  private String assetHistoryWorkerCron;

  @Value("${app.price-history-worker.cron}")
  private String priceHistoryWorkerCron;

  @Value("${app.pool-reserve-worker.cron}")
  private String poolReserveWorkerCron;

  @Value("${app.price-history-syncer.cron}")
  private String priceHistorySyncerCron;

  @Value("${app.price-history-syncer.duration}")
  private int priceHistorySyncerDuration;

  @Value("${app.price-history-syncer.interval}")
  private int priceHistorySyncerInterval;

  @Value("${app.asset-history-storage-guardian.cron}")
  private String assetHistoryStorageGuardianCron;

  @Value("${app.price-history-storage-guardian.cron}")
  private String priceHistoryStorageGuardianCron;

}
