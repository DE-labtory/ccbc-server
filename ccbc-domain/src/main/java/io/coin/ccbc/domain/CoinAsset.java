package io.coin.ccbc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CoinAsset {

  private Coin coin;
  private Asset depositableAsset;
  private Asset totalAsset;

}
