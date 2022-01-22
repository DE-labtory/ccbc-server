package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Coin;
import lombok.Getter;

@Getter
public class CoinBalance extends Balance {

  private final Coin coin;

  private CoinBalance(Coin coin, Amount amount) {
    this.coin = coin;
    this.amount = amount;
  }

  public static CoinBalance from(Coin coin, Amount amount) {
    return new CoinBalance(coin, amount);
  }
}
