package io.coin.ccbc.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class Price {

  @Column
  private double value;

  public static Price from(double value) {
    return new Price(value);
  }

  public static Price zero() {
    return new Price(0.0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Price price = (Price) o;
    return Double.compare(price.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
