package io.coin.ccbc.support;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CoinValueCalculator {

  public static BigDecimal getValue(BigInteger amount, double coinPrice, int decimals) {
    return new BigDecimal(amount).multiply(BigDecimal.valueOf(coinPrice))
        .divide(new BigDecimal(10).pow(decimals));
  }
}
