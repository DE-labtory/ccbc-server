package io.coin.ccbc.klayfi.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reward extends Balance {

  public Reward(Address coinAddress, Amount coinAmount) {
    super(coinAddress, coinAmount);
  }
}
