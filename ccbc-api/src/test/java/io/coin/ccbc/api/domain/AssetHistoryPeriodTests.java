package io.coin.ccbc.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("AssetHistoryPeriod")
public class AssetHistoryPeriodTests {

  @Nested
  class GetCloseTime {

    @Nested
    @DisplayName("Year 기준")
    class Year {

      @Test
      void getCloseTime() {
        // 2021-09-19T1:00 -> 2021-09-19T00:00
        // 2021-09-19T12:59 -> 2021-09-19T00:00

        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("YEAR");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 1, 0),
            LocalDateTime.of(2021, 9, 19, 12, 59)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 0, 0));
        });
      }
    }

    @Nested
    @DisplayName("Month 기준")
    class Month {

      @Test
      void getCloseTime() {
        // 2021-09-19T01:00 -> 2021-09-19T01:00
        // 2021-09-19T01:01 -> 2021-09-19T01:00
        // 2021-09-19T01:10 -> 2021-09-19T01:00
        // 2021-09-19T01:12 -> 2021-09-19T01:00

        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("MONTH");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 1, 0),
            LocalDateTime.of(2021, 9, 19, 1, 1),
            LocalDateTime.of(2021, 9, 19, 1, 10),
            LocalDateTime.of(2021, 9, 19, 1, 12)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 1, 0));
        });
      }
    }

    @Nested
    @DisplayName("Week 기준")
    class Week {

      @Test
      void getCloseTime() {
        // 2021-09-19T01:00 -> 2021-09-19T01:00
        // 2021-09-19T01:01 -> 2021-09-19T01:00
        // 2021-09-19T01:10 -> 2021-09-19T01:00
        // 2021-09-19T01:12 -> 2021-09-19T01:00

        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("WEEK");
        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 1, 0),
            LocalDateTime.of(2021, 9, 19, 1, 1),
            LocalDateTime.of(2021, 9, 19, 1, 10),
            LocalDateTime.of(2021, 9, 19, 1, 12)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 1, 0));
        });
      }
    }

    @Nested
    @DisplayName("Day 기준")
    class Day {

      @Test
      @DisplayName("현재 분이 0분 이상 ~ 15분 미만 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsBetween0And14Minutes() {
        // 2021-09-19T12:14 -> 2021-09-18T12:00
        // 2021-09-19T12:10 -> 2021-09-18T12:00
        // 2021-09-19T12:17 -> 2021-09-18T12:00
        // 2021-09-19T12:01 -> 2021-09-18T12:00
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 14),
            LocalDateTime.of(2021, 9, 19, 12, 10),
            LocalDateTime.of(2021, 9, 19, 12, 7),
            LocalDateTime.of(2021, 9, 19, 12, 1)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 00));
        });
      }

      @Test
      @DisplayName("현재 분이 15분 이상 ~ 30분 미만 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsBetween15And29Minutes() {
        // 2021-09-19T12:15 -> 2021-09-18T12:15
        // 2021-09-19T12:17 -> 2021-09-18T12:15
        // 2021-09-19T12:20 -> 2021-09-18T12:15
        // 2021-09-19T12:25 -> 2021-09-18T12:15
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 15),
            LocalDateTime.of(2021, 9, 19, 12, 17),
            LocalDateTime.of(2021, 9, 19, 12, 20),
            LocalDateTime.of(2021, 9, 19, 12, 25)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 15));
        });
      }

      @Test
      @DisplayName("현재 분이 30분이상 45분 미만 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsBetween30And44Minutes() {
        // 2021-09-19T12:30 -> 2021-09-19T12:30
        // 2021-09-19T12:31 -> 2021-09-19T12:30
        // 2021-09-19T12:40 -> 2021-09-19T12:30
        // 2021-09-19T12:44 -> 2021-09-19T12:30
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 30),
            LocalDateTime.of(2021, 9, 19, 12, 31),
            LocalDateTime.of(2021, 9, 19, 12, 40),
            LocalDateTime.of(2021, 9, 19, 12, 44)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 30));
        });
      }

      @Test
      @DisplayName("현재 분이 55분 이상 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsGreaterThan45Minutes() {
        // 2021-09-19T12:45 -> 2021-09-18T12:45
        // 2021-09-19T12:46 -> 2021-09-18T12:45
        // 2021-09-19T12:48 -> 2021-09-18T12:45
        // 2021-09-19T12:52 -> 2021-09-18T12:45
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 45),
            LocalDateTime.of(2021, 9, 19, 12, 46),
            LocalDateTime.of(2021, 9, 19, 12, 48),
            LocalDateTime.of(2021, 9, 19, 12, 52)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 45));
        });
      }
    }

    @Nested
    @DisplayName("4hour 기준")
    class FourHour {

      @Test
      @DisplayName("현재 분이 0분 이상 ~ 5분 미만 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsBetween0And14Minutes() {
        // 2021-09-19T12:03 -> 2021-09-18T12:00
        // 2021-09-19T12:01 -> 2021-09-18T12:00
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 3),
            LocalDateTime.of(2021, 9, 19, 12, 1)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 0));
        });
      }

      @Test
      @DisplayName("현재 분이 5분 이상 ~ 10분 미만 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsBetween15And29Minutes() {
        // 2021-09-19T12:09 -> 2021-09-18T12:05
        // 2021-09-19T12:05 -> 2021-09-18T12:05
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 9),
            LocalDateTime.of(2021, 9, 19, 12, 5)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 5));
        });
      }

      @Test
      @DisplayName("현재 분이 10분이상 15분 미만 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsBetween30And44Minutes() {
        // 2021-09-19T12:12 -> 2021-09-18T12:10
        // 2021-09-19T12:14 -> 2021-09-18T12:10
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 12),
            LocalDateTime.of(2021, 9, 19, 12, 14)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 10));
        });
      }

      @Test
      @DisplayName("현재 분이 55분 이상 일경우")
      void getCloseTime_WhenTheCurrentMinuteIsGreaterThan45Minutes() {
        // 2021-09-19T12:56 -> 2021-09-19T12:55
        // 2021-09-19T12:59 -> 2021-09-19T12:55
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 56),
            LocalDateTime.of(2021, 9, 19, 12, 59)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime closeTime = assetHistoryPeriod.getCloseTime(localDateTime);
          assertThat(closeTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 12, 55));
        });
      }
    }
  }

  @Nested
  class GetOpenTime {

    @Nested
    @DisplayName("Year 기준")
    class Year {

      @Test
      void getOpenTime() {
        // 2021-09-19T1:00 -> 2020-09-20T00:00
        // 2021-09-19T12:59 -> 2020-09-20T00:00

        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("YEAR");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 1, 0),
            LocalDateTime.of(2021, 9, 19, 12, 59)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2020, 9, 20, 0, 0));
        });
      }
    }

    @Nested
    @DisplayName("Month 기준")
    class Month {

      @Test
      void getOpenTime() {
        // 2021-09-19T12:00 -> 2021-09-12T16:00 (7일 전 + 4시간 추가)
        // 2021-09-19T12:59 -> 2021-09-12T16:00 (7일 전 + 4시간 추가)

        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("MONTH");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 0),
            LocalDateTime.of(2021, 9, 19, 12, 59)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 8, 20, 16, 0));
        });
      }
    }

    @Nested
    @DisplayName("Week 기준")
    class Week {

      @Test
      void getOpenTime() {
        // 2021-09-19T12:00 -> 2021-09-12T13:00
        // 2021-09-19T12:59 -> 2021-09-12T13:00

        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("WEEK");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 0),
            LocalDateTime.of(2021, 9, 19, 12, 59)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 12, 13, 0));
        });
      }
    }

    @Nested
    @DisplayName("Day 기준")
    class Day {

      @Test
      @DisplayName("현재 분이 0분 이상 ~ 15분 미만 일경우")
      void getOpenTime_WhenTheCurrentMinuteIsBetween0And14Minutes() {
        // 2021-09-19T12:14 -> 2021-09-18T12:15
        // 2021-09-19T12:10 -> 2021-09-18T12:15
        // 2021-09-19T12:17 -> 2021-09-18T12:15
        // 2021-09-19T12:1 -> 2021-09-18T12:15
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 14),
            LocalDateTime.of(2021, 9, 19, 12, 10),
            LocalDateTime.of(2021, 9, 19, 12, 7),
            LocalDateTime.of(2021, 9, 19, 12, 1)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 18, 12, 15));
        });
      }

      @Test
      @DisplayName("현재 분이 15분 이상 ~ 30분 미만 일경우")
      void getOpenTime_WhenTheCurrentMinuteIsBetween15And29Minutes() {
        // 2021-09-19T12:15 -> 2021-09-18T12:30
        // 2021-09-19T12:17 -> 2021-09-18T12:30
        // 2021-09-19T12:20 -> 2021-09-18T12:30
        // 2021-09-19T12:25 -> 2021-09-18T12:30
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 15),
            LocalDateTime.of(2021, 9, 19, 12, 17),
            LocalDateTime.of(2021, 9, 19, 12, 20),
            LocalDateTime.of(2021, 9, 19, 12, 25)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 18, 12, 30));
        });
      }

      @Test
      @DisplayName("현재 분이 30분이상 45분 미만 일경우")
      void getOpenTime_WhenTheCurrentMinuteIsBetween30And44Minutes() {
        // 2021-09-19T12:30 -> 2021-09-18T12:45
        // 2021-09-19T12:31 -> 2021-09-18T12:45
        // 2021-09-19T12:40 -> 2021-09-18T12:45
        // 2021-09-19T12:44 -> 2021-09-18T12:45
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 30),
            LocalDateTime.of(2021, 9, 19, 12, 31),
            LocalDateTime.of(2021, 9, 19, 12, 40),
            LocalDateTime.of(2021, 9, 19, 12, 44)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 18, 12, 45));
        });
      }

      @Test
      @DisplayName("현재 분이 45분 이상인 경우")
      void getOpenTime_WhenTheCurrentMinuteIsGreaterThan45Minutes() {
        // 2021-09-19T12:46 -> 2021-09-18T13:00
        // 2021-09-19T12:49 -> 2021-09-18T13:00
        // 2021-09-19T12:52 -> 2021-09-18T13:00
        // 2021-09-19T12:55 -> 2021-09-18T13:00
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("DAY");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 46),
            LocalDateTime.of(2021, 9, 19, 12, 49),
            LocalDateTime.of(2021, 9, 19, 12, 52),
            LocalDateTime.of(2021, 9, 19, 12, 55)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 18, 13, 0));
        });
      }
    }

    @Nested
    @DisplayName("4hour 기준")
    class FourHour {

      @Test
      @DisplayName("현재 분이 0분 이상 ~ 5분 미만 일경우")
      void getOpenTime_WhenTheCurrentMinuteIsBetween0And14Minutes() {
        // 2021-09-19T12:04 -> 2021-09-19T08:05
        // 2021-09-19T12:01 -> 2021-09-19T08:05
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 4),
            LocalDateTime.of(2021, 9, 19, 12, 1)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 8, 5));
        });
      }

      @Test
      @DisplayName("현재 분이 5분 이상 ~ 10분 미만 일경우")
      void getOpenTime_WhenTheCurrentMinuteIsBetween15And29Minutes() {
        // 2021-09-19T12:09 -> 2021-09-19T08:10
        // 2021-09-19T12:05 -> 2021-09-19T08:10
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 9),
            LocalDateTime.of(2021, 9, 19, 12, 5)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 8, 10));
        });
      }

      @Test
      @DisplayName("현재 분이 10분이상 15분 미만 일경우")
      void getOpenTime_WhenTheCurrentMinuteIsBetween30And44Minutes() {
        // 2021-09-19T12:12 -> 2021-09-19T08:15
        // 2021-09-19T12:11 -> 2021-09-19T08:15
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 12),
            LocalDateTime.of(2021, 9, 19, 12, 11)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 8, 15));
        });
      }

      @Test
      @DisplayName("현재 분이 55분 이상인 경우")
      void getOpenTime_WhenTheCurrentMinuteIsGreaterThan55Minutes() {
        // 2021-09-19T12:55 -> 2021-09-18T13:00
        // 2021-09-19T12:56 -> 2021-09-18T13:00
        // 2021-09-19T12:57 -> 2021-09-18T13:00
        // 2021-09-19T12:59 -> 2021-09-18T13:00
        AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of("fourHour");

        List<LocalDateTime> inputs = Arrays.asList(
            LocalDateTime.of(2021, 9, 19, 12, 55),
            LocalDateTime.of(2021, 9, 19, 12, 56),
            LocalDateTime.of(2021, 9, 19, 12, 57),
            LocalDateTime.of(2021, 9, 19, 12, 59)
        );

        inputs.forEach(localDateTime -> {
          LocalDateTime openTime = assetHistoryPeriod.getOpenTime(localDateTime);
          assertThat(openTime).isEqualTo(LocalDateTime.of(2021, 9, 19, 9, 0));
        });
      }
    }
  }
}