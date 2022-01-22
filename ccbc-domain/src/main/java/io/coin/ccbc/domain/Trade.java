package io.coin.ccbc.domain;

import java.math.BigInteger;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade {

  private List<Path> route;
  private Coin fromCoin;
  private BigInteger fromAmount;
  private Coin toCoin;
  private BigInteger toAmount;
  private double priceImpact;

  public static Trade of(
      List<Path> route,
      Coin fromCoin,
      BigInteger fromAmount,
      Coin toCoin,
      BigInteger toAmount,
      double priceImpact
  ) {
    return new Trade(
        route,
        fromCoin,
        fromAmount,
        toCoin,
        toAmount,
        priceImpact
    );
  }
}
