package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.support.BigIntegerToStringSerializer;
import io.coin.ccbc.support.CoinValueCalculator;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressAssetDto {

  private String symbol;
  private String name;
  private int decimals;
  @JsonSerialize(using = BigIntegerToStringSerializer.class)
  private BigInteger depositedAmount;
  @JsonSerialize(using = BigIntegerToStringSerializer.class)
  private BigInteger depositableAmount;
  @JsonSerialize(using = BigIntegerToStringSerializer.class)
  private BigInteger interestAmount;
  private double totalValue;
  private double interestValue;
  private double coinPrice;

  public AddressAssetDto(
      CoinDto coinDto,
      BigInteger depositableAmount,
      BigInteger depositedAmount,
      BigInteger interestAmount,
      double coinPrice
  ) {
    this.symbol = coinDto.getSymbol();
    this.name = coinDto.getName();
    this.decimals = coinDto.getDecimals();
    this.depositedAmount = depositedAmount;
    this.depositableAmount = depositableAmount;
    this.totalValue = CoinValueCalculator.getValue(
        depositableAmount.add(depositedAmount),
        coinPrice,
        this.decimals
    ).doubleValue();
    this.interestAmount = interestAmount;
    this.interestValue = CoinValueCalculator.getValue(
        interestAmount,
        coinPrice,
        this.decimals
    ).doubleValue();
    this.coinPrice = coinPrice;
  }
}
