package io.coin.ccbc.worker.domain.history;

import io.coin.ccbc.domain.Account;
import io.coin.ccbc.domain.Value;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetHistoryWritingItem {

  private final Account account;
  private final Value assetValue;
  private final LocalDateTime workingTime;

  public static AssetHistoryWritingItem of(
      Account account,
      Value assetValue,
      LocalDateTime workingTime
  ) {
    return new AssetHistoryWritingItem(account, assetValue, workingTime);
  }
}
