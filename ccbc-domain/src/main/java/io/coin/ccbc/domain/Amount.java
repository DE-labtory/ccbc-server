package io.coin.ccbc.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {

  private final BigInteger value;

  public static Amount of(BigInteger value) {
    return new Amount(value);
  }

  public static Amount zero() {
    return new Amount(BigInteger.ZERO);
  }

  public boolean isEqualTo(Amount amount) {
    return this.value.compareTo(amount.value) == 0;
  }

  public BigDecimal calculateDecimals(Integer decimals) {
    return new BigDecimal(this.value)
        .divide(BigDecimal.TEN.pow(decimals));
  }

  public Amount add(Amount amount) {
    return new Amount(this.value.add(amount.value));
  }

  public boolean isZero() {
    return this.value.equals(BigInteger.ZERO);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Amount)) {
      return false;
    }
    Amount amount = (Amount) o;
    return value.compareTo(amount.value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
