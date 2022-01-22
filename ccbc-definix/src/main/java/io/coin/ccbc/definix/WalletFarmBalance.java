package io.coin.ccbc.definix;

import io.coin.ccbc.domain.Address;
import java.math.BigInteger;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletFarmBalance {

  private Address farmAddress;
  private Address walletAddress;
  private BigInteger lpAmount;
  private BigInteger token0Balance;
  private BigInteger token1Balance;
  private List<WalletFarmInterest> interests;

  public static WalletFarmBalance of(
      Address farmAddress,
      Address walletAddress,
      BigInteger lpAmount,
      BigInteger token0Balance,
      BigInteger token1Balance,
      List<WalletFarmInterest> interests
  ) {
    return new WalletFarmBalance(
        farmAddress,
        walletAddress,
        lpAmount,
        token0Balance,
        token1Balance,
        interests
    );
  }
}
