package io.coin.ccbc.worker.config;

import io.coin.ccbc.domain.Time;
import io.coin.ccbc.worker.application.AssetHistoryStorageGuardian;
import io.coin.ccbc.worker.application.AssetHistoryWorker;
import io.coin.ccbc.worker.application.PriceHistoryStorageGuardian;
import io.coin.ccbc.worker.application.PriceHistorySyncer;
import io.coin.ccbc.worker.application.PriceHistoryWorker;
import io.coin.ccbc.worker.application.PriceWorker;
import io.coin.ccbc.worker.config.prorperty.WorkerProperties;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@EnableScheduling
public class SchedulerConfig {

  private final SchedulerFactoryBean schedulerFactory;

  public SchedulerConfig(final SchedulerFactoryBean schedulerFactory) {
    this.schedulerFactory = schedulerFactory;
  }

  @Bean
  public Scheduler priceScheduler(WorkerProperties properties) throws SchedulerException {
    JobDetail jobDetail = this.createJobDetail(PriceWorker.class);
    Trigger trigger = this.createTrigger(
        jobDetail,
        this.createDefaultCronScheduleBuilder(properties.getPriceWorkerCron())
    );
    return this.initScheduler(jobDetail, trigger);
  }

  @Bean
  public Scheduler assetHistoryScheduler(WorkerProperties properties) throws SchedulerException {
    JobDetail jobDetail = this.createJobDetail(AssetHistoryWorker.class);
    Trigger trigger = this.createTrigger(
        jobDetail,
        this.createDefaultCronScheduleBuilder(properties.getAssetHistoryWorkerCron())
    );
    return this.initScheduler(jobDetail, trigger);
  }

  @Bean
  public Scheduler priceHistoryScheduler(WorkerProperties properties) throws SchedulerException {
    JobDetail jobDetail = this.createJobDetail(PriceHistoryWorker.class);
    Trigger trigger = this.createTrigger(
        jobDetail,
        this.createDefaultCronScheduleBuilder(properties.getPriceHistoryWorkerCron())
    );
    return this.initScheduler(jobDetail, trigger);
  }

  @Bean
  public Scheduler priceHistorySyncerScheduler(WorkerProperties properties)
      throws SchedulerException {
    JobDetail jobDetail = this.createJobDetail(PriceHistorySyncer.class);
    Trigger trigger = this.createTrigger(
        jobDetail,
        this.createDefaultCronScheduleBuilder(properties.getPriceHistorySyncerCron())
    );
    return this.initScheduler(jobDetail, trigger);
  }

  @Bean
  public Scheduler assetHistoryStorageGuardianScheduler(WorkerProperties properties) throws SchedulerException {
    JobDetail jobDetail = this.createJobDetail(AssetHistoryStorageGuardian.class);
    Trigger trigger = this.createTrigger(
        jobDetail,
        this.createDefaultCronScheduleBuilder(properties.getAssetHistoryStorageGuardianCron())
    );
    return this.initScheduler(jobDetail, trigger);
  }

  @Bean
  public Scheduler priceHistoryStorageGuardianScheduler(WorkerProperties properties) throws SchedulerException {
    JobDetail jobDetail = this.createJobDetail(PriceHistoryStorageGuardian.class);
    Trigger trigger = this.createTrigger(
        jobDetail,
        this.createDefaultCronScheduleBuilder(properties.getPriceHistoryStorageGuardianCron())
    );
    return this.initScheduler(jobDetail, trigger);
  }

  private Scheduler initScheduler(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
    Scheduler scheduler = this.schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    return scheduler;
  }

  private JobDetail createJobDetail(Class jobClazz) {
    return JobBuilder.newJob().ofType(jobClazz).build();
  }

  private Trigger createTrigger(JobDetail job, CronScheduleBuilder cronSchedule) {
    return TriggerBuilder.newTrigger()
        .forJob(job)
        .withSchedule(cronSchedule)
        .build();
  }

  private CronScheduleBuilder createDefaultCronScheduleBuilder(String cronExpression) {
    return CronScheduleBuilder
        .cronSchedule(cronExpression)
        .inTimeZone(Time.getDefaultTimeZone());
  }
}
