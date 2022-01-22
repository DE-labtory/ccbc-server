package io.coin.ccbc.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Asset {

  private Coin coin;
  private Amount amount;
  private Value value;

  public Asset(Coin coin, Amount amount, Value value) {
    this.coin = coin;
    this.amount = amount;
    this.value = value;
  }
  public Asset(Coin coin, Amount amount){
    this.coin = coin;
    this.amount = amount;
    this.value = Value.of(amount,coin.getPrice(),coin.getDecimals());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Asset)) {
      return false;
    }
    Asset asset = (Asset) o;
    return coin.equals(asset.coin) && amount.equals(asset.amount) && value.equals(asset.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coin, amount, value);
  }
}
