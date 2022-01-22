package io.coin.ccbc.klayfi.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Balance {

  private Address coinAddress;
  private Amount coinAmount;
}
