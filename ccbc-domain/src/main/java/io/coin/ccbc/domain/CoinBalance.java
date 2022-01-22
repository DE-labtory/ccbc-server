package io.coin.ccbc.domain;

import java.math.BigInteger;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinBalance {

  private final Coin coin;
  private final Amount amount;

  public static CoinBalance of(Coin coin, WalletCoinBalance balance) {
    return new CoinBalance(coin, Amount.of(balance.getAmount()));
  }

  public static CoinBalance of(Coin coin, BigInteger amount) {
    return new CoinBalance(coin, Amount.of(amount));
  }

  public static CoinBalance of(Coin coin, Amount amount) {
    return new CoinBalance(coin, amount);
  }

  public boolean isZero() {
    return this.amount.isZero();
  }

  public CoinBalance add(CoinBalance c) {
    if (!Objects.equals(coin, c.coin)) {
      throw new IllegalStateException("cannot add different coin balance");
    }

    return new CoinBalance(
        coin,
        amount.add(c.amount)
    );
  }
}
