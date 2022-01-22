package io.coin.ccbc.klayfi.domain;

import lombok.Getter;

@Getter
public class VaultBalance {

  private final Balance coin0Balance;
  private final Balance coin1Balance;

  public VaultBalance(Balance coin0Balance, Balance coin1Balance) {
    this.coin0Balance = coin0Balance;
    this.coin1Balance = coin1Balance;
  }
}
