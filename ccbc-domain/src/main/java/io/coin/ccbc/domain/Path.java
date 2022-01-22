package io.coin.ccbc.domain;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Path {

  private Coin fromCoin;
  private Coin toCoin;
  private DexPair targetDexPair;

  public static Path of(
      Coin fromCoin,
      Coin toCoin,
      DexPair targetDexPair
  ) {
    return new Path(
        fromCoin,
        toCoin,
        targetDexPair
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Path)) {
      return false;
    }

    Path path = (Path) o;
    return Objects.equals(fromCoin, path.fromCoin) && Objects
        .equals(toCoin, path.toCoin) &&
        Objects.equals(targetDexPair.getAddress(), path.targetDexPair.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.fromCoin.getAddress(), this.toCoin.getAddress(),
            this.targetDexPair.getAddress());
  }

}
