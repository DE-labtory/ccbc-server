package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author Bomi
 * @date 2021/08/04
 */

public class Time {

  private static final String DEFAULT_REGION = "Asia/Seoul";
  private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of(DEFAULT_REGION);

  public static ZoneId getDefaultZoneId() {
    return DEFAULT_ZONE_ID;
  }

  public static TimeZone getDefaultTimeZone() {
    return TimeZone.getTimeZone(DEFAULT_ZONE_ID);
  }

  public static LocalDateTime now() {
    return LocalDateTime.now(DEFAULT_ZONE_ID);
  }

  public static LocalDateTime timeBefore24Hours() {
    return now().minusHours(24L);
  }

  public static LocalDateTime nearbyWorkingTime(LocalDateTime time) {
    time = time.withSecond(0).withNano(0);
    int minutes = time.getMinute();
    if (minutes <= 7) {
      return time.withMinute(0);
    }
    if (minutes <= 22) {
      return time.withMinute(15);
    }
    if (minutes <= 37) {
      return time.withMinute(30);
    }
    if (minutes <= 52) {
      return time.withMinute(45);
    }
    return time.plusHours(1).withMinute(0);
  }

}
