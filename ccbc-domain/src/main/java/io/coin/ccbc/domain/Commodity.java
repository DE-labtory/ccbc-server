package io.coin.ccbc.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Commodity {

  private String name;
  private Address address;
  @Setter
  private double apr;
  private double tvl;
  private List<Coin> relatedCoin;

  public Commodity(
      String name,
      Address address,
      double apr,
      double tvl,
      List<Coin> relatedCoins
  ) {
    this.name = name;
    this.address = address;
    this.apr = apr;
    this.tvl = tvl;
    this.relatedCoin = relatedCoins;
  }

  public static Commodity withoutApr(
      String name,
      Address address,
      List<Coin> relatedCoins
  ) {
    return new Commodity(
        name,
        address,
        0,
        0,
        relatedCoins
    );
  }

  public void updateApr(double apr) {
    this.apr = apr;
  }

  public void updateTvl(double tvl) {
    this.tvl = tvl;
  }
}
