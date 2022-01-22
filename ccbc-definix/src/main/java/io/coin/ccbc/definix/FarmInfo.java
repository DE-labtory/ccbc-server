package io.coin.ccbc.definix;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import java.math.BigInteger;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FarmInfo {

  private Address farmAddress;
  private BigInteger pid;
  private BigInteger lpAmount;
  private BigInteger token0Reserve;
  private BigInteger token1Reserve;
  // Map<Coin Address, value>
  private Map<Address, Amount> interestsPerBlock;

  public static FarmInfo of(
      Address farmAddress,
      BigInteger pid,
      BigInteger lpAmount,
      BigInteger token0Reserve,
      BigInteger token1Reserve,
      Map<Address, Amount> interestsPerBlock
  ) {
    return new FarmInfo(
        farmAddress,
        pid,
        lpAmount,
        token0Reserve,
        token1Reserve,
        interestsPerBlock
    );
  }
}
