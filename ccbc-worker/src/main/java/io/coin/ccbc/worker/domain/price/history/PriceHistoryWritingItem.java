package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.Coin;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceHistoryWritingItem {

  private final Coin coin;
  private final LocalDateTime collectedAt;

  public static PriceHistoryWritingItem of(Coin coin, LocalDateTime collectedAt) {
    return new PriceHistoryWritingItem(coin, collectedAt);
  }
}
