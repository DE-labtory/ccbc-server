package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Value;
import io.coin.ccbc.support.BigDecimalToStringSerializer;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinAssetDto {

  private final CoinPriceDto coin;

  @JsonSerialize(using = BigDecimalToStringSerializer.class)
  private final BigDecimal depositableAmount;

  private final Double depositableValue;

  @JsonSerialize(using = BigDecimalToStringSerializer.class)
  private final BigDecimal totalAssetAmount;

  private final Double totalAssetValue;

  public static CoinAssetDto of(
      Coin coin,
      Double previousPrice,
      Amount depositableAmount,
      Value depositableValue,
      Amount totalAssetAmount,
      Value totalAssetValue
  ) {
    return new CoinAssetDto(
        CoinPriceDto.of(coin, previousPrice),
        depositableAmount.calculateDecimals(coin.getDecimals()),
        depositableValue.getValue().doubleValue(),
        totalAssetAmount.calculateDecimals(coin.getDecimals()),
        totalAssetValue.getValue().doubleValue()
    );
  }
}