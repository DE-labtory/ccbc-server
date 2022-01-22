package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.Coin;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceHistoryReadingItem {

  private final List<Coin> coins;
  private final LocalDateTime collectedAt;

  public static PriceHistoryReadingItem of(
      final List<Coin> coins,
      final LocalDateTime collectedAt
  ) {
    return new PriceHistoryReadingItem(coins, collectedAt);
  }

}
