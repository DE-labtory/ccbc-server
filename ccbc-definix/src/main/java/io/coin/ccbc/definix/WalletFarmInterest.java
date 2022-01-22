package io.coin.ccbc.definix;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WalletFarmInterest {

  private Address farmAddress;
  private Address coinAddress;
  private Amount receivableAmount;
  private Amount receivedAmount;

  public static WalletFarmInterest of(
      Address poolAddress,
      Address coinAddress,
      Amount receivableAmount,
      Amount receivedAmount
  ) {

    return new WalletFarmInterest(
        poolAddress,
        coinAddress,
        receivableAmount,
        receivedAmount
    );
  }
}
