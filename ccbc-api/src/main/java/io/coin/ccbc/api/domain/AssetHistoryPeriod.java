package io.coin.ccbc.api.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum AssetHistoryPeriod {
  FOUR_HOUR("fourHour", Duration.ofHours(4), Duration.ofMinutes(5)),
  DAY("day", Duration.ofDays(1), Duration.ofMinutes(15)),
  WEEK("week", Duration.ofDays(7), Duration.ofHours(1)),
  MONTH("month", Duration.ofDays(30), Duration.ofHours(4)),
  YEAR("year", Duration.ofDays(365), Duration.ofDays(1));

  private final String name;
  private final Duration interval;
  private final Duration period;

  AssetHistoryPeriod(String name, Duration period, Duration interval) {
    this.name = name;
    this.period = period;
    this.interval = interval;
  }

  public static AssetHistoryPeriod of(String name) {
    return Arrays.stream(values())
        .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(
                String.format("'%s' is not supported balanceHistoryPeriod", name)));
  }

  public LocalDateTime getCloseTime(LocalDateTime time) {
    LocalDateTime baseTime = time.withSecond(0).withNano(0);
    switch (this) {
      case FOUR_HOUR:
        return this.getCloseTimeByFourHour(baseTime, time);
      case DAY:
        return this.getCloseTimeByDay(baseTime, time);
      case WEEK:
      case MONTH:
        return baseTime.withMinute(0);
      case YEAR:
        return baseTime.withMinute(0).withHour(0);
      default:
        throw new IllegalArgumentException(
            String.format("'%s' is not supported AssetHistoryPeriod", this.getName()));
    }
  }

  public LocalDateTime getOpenTime(LocalDateTime time) {
    LocalDateTime baseTime = this.getStartDate(time).withSecond(0).withNano(0);
    switch (this) {
      case FOUR_HOUR:
        return this.getOpenTimeByFourHour(baseTime, time);
      case DAY:
        return this.getOpenTimeByDay(baseTime, time);
      case WEEK:
      case MONTH:
        baseTime = baseTime.withMinute(0);
        return baseTime.plus(this.interval);
      case YEAR:
        return baseTime.withMinute(0).withHour(0).plus(this.interval);
      default:
        throw new IllegalArgumentException(
            String.format("'%s' is not supported AssetHistoryPeriod", this.getName()));
    }
  }

  public List<LocalDateTime> getIntervalTimes(LocalDateTime openTime, LocalDateTime closeTime) {
    List<LocalDateTime> times = new ArrayList<>();
    for (LocalDateTime time = openTime; time.isEqual(closeTime) || time.isBefore(closeTime);
        time = time.plus(interval)) {
      times.add(time);
    }
    return times;
  }

  private LocalDateTime getStartDate(LocalDateTime time) {
    return time.minus(this.period);
  }

  private LocalDateTime getCloseTimeByFourHour(LocalDateTime baseTime, LocalDateTime time) {
    int minute = time.getMinute();
    for (int i = 0; i < 11; i++) {
      if (minute < (i + 1) * FOUR_HOUR.interval.toMinutesPart()) {
        return baseTime.withMinute(i * FOUR_HOUR.interval.toMinutesPart());
      }
    }

    return baseTime.withMinute(55);
  }

  private LocalDateTime getOpenTimeByFourHour(LocalDateTime baseTime, LocalDateTime time) {
    int minute = time.getMinute();
    for (int i = 0; i < 11; i++) {
      if (minute < (i + 1) * FOUR_HOUR.interval.toMinutesPart()) {
        return baseTime.withMinute((i + 1) * FOUR_HOUR.interval.toMinutesPart());
      }
    }

    return baseTime.withMinute(0).plusHours(1);
  }

  private LocalDateTime getCloseTimeByDay(LocalDateTime baseTime, LocalDateTime time) {
    int minute = time.getMinute();
    for (int i = 0; i < 3; i++) {
      if (minute < (i + 1) * DAY.interval.toMinutesPart()) {
        return baseTime.withMinute(i * DAY.interval.toMinutesPart());
      }
    }

    return baseTime.withMinute(45);
  }

  private LocalDateTime getOpenTimeByDay(LocalDateTime baseTime, LocalDateTime time) {
    int minute = time.getMinute();
    for (int i = 0; i < 3; i++) {
      if (minute < (i + 1) * DAY.interval.toMinutesPart()) {
        return baseTime.withMinute((i + 1) * DAY.interval.toMinutesPart());
      }
    }

    return baseTime.withMinute(0).plusHours(1);
  }
}
