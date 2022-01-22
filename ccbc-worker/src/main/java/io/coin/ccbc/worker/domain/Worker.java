package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Time;
import java.time.LocalDateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface Worker extends Job {

  void work(LocalDateTime workingTime);

  @Override
  default void execute(JobExecutionContext context) throws JobExecutionException {
    this.work(
        context.getScheduledFireTime()
            .toInstant()
            .atZone(Time.getDefaultZoneId())
            .toLocalDateTime()
    );
  }
}
