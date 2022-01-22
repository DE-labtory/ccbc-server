package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.support.BigIntegerToStringSerializer;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InterestDto extends CoinDto {

  @JsonSerialize(using = BigIntegerToStringSerializer.class)
  private final BigInteger amount;
  private final double coinPrice;

  public InterestDto(CoinDto coinDto, BigInteger amount, double coinPrice) {
    super(
        coinDto.getId(),
        coinDto.getSymbol(),
        coinDto.getName(),
        coinDto.getAddress(),
        coinDto.getDecimals(),
        coinDto.getSymbolImageUrl()
    );
    this.amount = amount;
    this.coinPrice = coinPrice;
  }
}
