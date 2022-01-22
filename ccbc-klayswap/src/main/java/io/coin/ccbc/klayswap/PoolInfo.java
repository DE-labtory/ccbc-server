package io.coin.ccbc.klayswap;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import java.math.BigInteger;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PoolInfo {

  private Address poolAddress;
  private BigInteger lpAmount;
  private BigInteger token0Reserve;
  private BigInteger token1Reserve;
  // Map<Coin Address, value>
  private Map<Address, Amount> interestsPerBlock;

  public static PoolInfo of(
      Address poolAddress,
      BigInteger lpAmount,
      BigInteger token0Reserve,
      BigInteger token1Reserve,
      Map<Address, Amount> interestsPerBlock
  ) {
    return new PoolInfo(
        poolAddress,
        lpAmount,
        token0Reserve,
        token1Reserve,
        interestsPerBlock
    );
  }

  @Nullable
  public Amount getInterestPerBlockByCoinAddress(Address coinAddress) {
    return this.interestsPerBlock.get(coinAddress);
  }
}
