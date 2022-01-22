package io.coin.ccbc.klayfi.infra.dto;

import java.math.BigInteger;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.generated.Uint256;

public class VaultInfo extends DynamicStruct {

  private final String token0Address;
  private final BigInteger depositedCoin0Amount;
  private final BigInteger earnedCoin0Amount;
  private final String token1Address;
  private final BigInteger depositedCoin1Amount;
  private final BigInteger earnedCoin1Amount;
  private final String kfiAddress;
  private final BigInteger earnedKfiAmount;

  public VaultInfo(
      Address token0Address,
      Uint256 depositedCoin0Amount,
      Uint256 earnedCoin0Amount,
      Address token1Address,
      Uint256 depositedCoin1Amount,
      Uint256 earnedCoin1Amount,
      Address kfiAddress,
      Uint256 earnedKfiAmount
  ) {
    super(
        token0Address,
        depositedCoin0Amount,
        earnedCoin0Amount,
        token1Address,
        depositedCoin1Amount,
        earnedCoin1Amount,
        kfiAddress,
        earnedKfiAmount
    );
    this.token0Address = token0Address.getValue();
    this.depositedCoin0Amount = depositedCoin0Amount.getValue();
    this.earnedCoin0Amount = earnedCoin0Amount.getValue();
    this.token1Address = token1Address.getTypeAsString();
    this.depositedCoin1Amount = depositedCoin1Amount.getValue();
    this.earnedCoin1Amount = earnedCoin1Amount.getValue();
    this.kfiAddress = kfiAddress.getValue();
    this.earnedKfiAmount = earnedKfiAmount.getValue();
  }
}
