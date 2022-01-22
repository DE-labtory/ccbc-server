package io.coin.ccbc.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Embeddable
public class Value implements Comparable<Value> {

  @Column
  private BigDecimal value;

  public static Value of(Amount amount, Price price, int decimals) {
    return new Value(
        amount.calculateDecimals(decimals)
            .multiply(BigDecimal.valueOf(price.getValue()))
    );
  }

  public static Value of(Amount amount, Coin coin) {
    return of(
        amount,
        coin.getPrice(),
        coin.getDecimals()
    );
  }

  public static Value of(Double value) {
    return new Value(
        BigDecimal.valueOf(value)
    );
  }

  public static Value zero() {
    return new Value(BigDecimal.ZERO);
  }

  public Value add(Value value) {
    return new Value(this.value.add(value.getValue()));
  }

  public Value multiply(Value v) {
    return new Value(this.value.multiply(v.getValue()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Value)) {
      return false;
    }
    Value value1 = (Value) o;
    return value.compareTo(value1.value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public int compareTo(Value value) {
    return this.getValue().compareTo(value.getValue());
  }
}