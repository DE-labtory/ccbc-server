package io.coin.ccbc.klayfi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KlayfiCommodityInfo {

  private String coin0Symbol;
  private String coin1Symbol;
  private Double apr;
  private Double tvl;
  private boolean isStaking;

  private KlayfiCommodityInfo(String coin0Symbol, String coin1Symbol, Double apr, Double tvl, boolean isStaking) {
    this.coin0Symbol = coin0Symbol;
    this.coin1Symbol = coin1Symbol;
    this.apr = apr;
    this.tvl = tvl;
    this.isStaking = isStaking;
  }

  public static KlayfiCommodityInfo Staking(String stakingCoin, Double apr, Double tvl) {
    return new KlayfiCommodityInfo(
        stakingCoin,
        null,
        apr,
        tvl,
        true
    );
  }

  public static KlayfiCommodityInfo Vault(String coin0Symbol, String coin1Symbol, Double apr, Double tvl) {
    return new KlayfiCommodityInfo(
        coin0Symbol,
        coin1Symbol,
        apr,
        tvl,
        false
    );
  }

  public boolean isMatchedPair(String coin0Symbol, String coin1Symbol) {
    if (this.isStaking) {
      return this.coin0Symbol.equalsIgnoreCase(coin0Symbol) && this.coin1Symbol
          .equalsIgnoreCase(coin1Symbol);
    }

    return (this.coin0Symbol.equalsIgnoreCase(coin0Symbol) && this.coin1Symbol
        .equalsIgnoreCase(coin1Symbol)) ||
        (this.coin0Symbol.equalsIgnoreCase(coin1Symbol) && this.coin1Symbol
            .equalsIgnoreCase(coin0Symbol));
  }
}
