package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Bomi
 * @date 2021/07/21
 */

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, String> {

  List<PriceHistory> findAllByCoinInAndCollectedAtIn(List<Coin> coins, List<LocalDateTime> times);

  List<PriceHistory> findAllByCoinAndCollectedAtIn(Coin coin, List<LocalDateTime> times);

  List<PriceHistory> findAllByCoinAndCollectedAtGreaterThanEqualAndCollectedAtLessThanEqual(
      Coin coin,
      LocalDateTime start,
      LocalDateTime end
  );

  Optional<PriceHistory> findTopByCoinAndCollectedAtBeforeOrderByCollectedAtDesc(Coin coin,
      LocalDateTime collectedAt);

  List<PriceHistory> findAllByCollectedAtGreaterThanEqualAndCollectedAtLessThan(LocalDateTime start,
      LocalDateTime end);

  @Modifying
  @Query(
      nativeQuery = true,
      value = "DELETE "
          + "FROM price_histories "
          + "WHERE collected_at < :collectedAt "
          + "AND to_char(collected_at, 'HH24:MI:SS') "
          + "NOT LIKE '%00:00:00%'"
  )
  void deleteAllByCollectedAtBeforeAndExceptMidnight(@Param("collectedAt") LocalDateTime collectedAt);

  @Modifying
  @Query(
      nativeQuery = true,
      value = "DELETE "
          + "FROM price_histories "
          + "WHERE collected_at > :start "
          + "AND collected_at < :end "
          + "AND NOT ( "
          + "to_char(collected_at, 'HH24:MI:SS') LIKE '%00:00:00%' "
          + "OR to_char(collected_at, 'HH24:MI:SS') LIKE '%04:00:00%' "
          + "OR to_char(collected_at, 'HH24:MI:SS') LIKE '%08:00:00%' "
          + "OR to_char(collected_at, 'HH24:MI:SS') LIKE '%12:00:00%' "
          + "OR to_char(collected_at, 'HH24:MI:SS') LIKE '%16:00:00%' "
          + "OR to_char(collected_at, 'HH24:MI:SS') LIKE '%16:00:00%' "
          + ")"
  )
  void deleteAllByCollectedAtBetweenAndExceptForEveryFourHoursAtMidnight(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end
  );

  @Modifying
  @Query(
      nativeQuery = true,
      value = "DELETE "
          + "FROM price_histories "
          + "WHERE collected_at > :start "
          + "AND collected_at < :end "
          + "AND to_char(collected_at, 'MI:SS') "
          + "NOT LIKE '%00:00%'"
  )
  void deleteAllByCollectedAtBetweenAndExceptOnTime(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end
  );
}
