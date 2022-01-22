package io.coin.ccbc.klayswap;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WalletPoolInterest {

  private Address poolAddress;
  private Address coinAddress;
  private Amount receivableAmount;
  private Amount receivedAmount;

  public static WalletPoolInterest of(
      Address poolAddress,
      Address coinAddress,
      Amount receivableAmount,
      Amount receivedAmount
  ) {

    return new WalletPoolInterest(
        poolAddress,
        coinAddress,
        receivableAmount,
        receivedAmount
    );
  }
}
