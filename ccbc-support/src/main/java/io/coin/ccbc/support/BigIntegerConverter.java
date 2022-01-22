package io.coin.ccbc.support;

import java.math.BigInteger;

public class BigIntegerConverter {

  public static BigInteger calculateByDecimals(BigInteger value, Integer decimal) {
    return BigInteger.valueOf(10).pow(decimal).multiply(value);
  }
}
