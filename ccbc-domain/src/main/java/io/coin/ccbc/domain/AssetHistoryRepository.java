package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, String> {

  List<AssetHistory> findAllByWalletAddressAndCollectedAtIn(Address address,
      List<LocalDateTime> collectedAt);

  @Query(
      nativeQuery = true,
      value = "DELETE "
          + "FROM asset_histories "
          + "WHERE collected_at < :collectedAt "
          + "AND to_char(collected_at, 'HH24:MI:SS') "
          + "NOT LIKE '%00:00:00%'"
  )
  void deleteAllByCollectedAtBeforeAndExceptMidnight(@Param("collectedAt") LocalDateTime collectedAt);

  @Query(
      nativeQuery = true,
      value = "DELETE "
          + "FROM asset_histories "
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

  @Query(
      nativeQuery = true,
      value = "DELETE "
          + "FROM asset_histories "
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
