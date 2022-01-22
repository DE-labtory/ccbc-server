package io.coin.ccbc.domain;

import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletCoinBalance {

  private Address coinAddress;
  private Address walletAddress;
  private BigInteger amount;

  public static WalletCoinBalance of(
      Address coinAddress,
      Address walletAddress,
      BigInteger amount
  ) {
    return new WalletCoinBalance(
        coinAddress,
        walletAddress,
        amount
    );
  }
}
