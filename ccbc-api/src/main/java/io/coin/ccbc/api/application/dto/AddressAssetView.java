package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.api.config.jackson.ValueToDoubleSerializer;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Value;
import io.coin.ccbc.support.BigDecimalToStringSerializer;
import io.coin.ccbc.support.BigIntegerToStringSerializer;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressAssetView {

  private String protocol;
  private String assetName;
  @JsonSerialize(using = BigIntegerToStringSerializer.class)
  private BigInteger amount;
  @JsonSerialize(using = ValueToDoubleSerializer.class)
  private Value value;
  private List<CoinDto> relatedCoins;

  public static AddressAssetView fromCommodityAsset(
      String protocol,
      String assetName,
      Value value,
      List<Coin> relatedCoins
  ) {
    return new AddressAssetView(
        protocol,
        assetName,
        BigInteger.ZERO,
        value,
        relatedCoins
            .stream()
            .map(CoinDto::from)
            .collect(Collectors.toList())
    );
  }

  public static AddressAssetView fromCoinAsset(
      Coin coin,
      Amount amount
  ) {
    return new AddressAssetView(
        "",
        coin.getName(),
        amount.calculateDecimals(coin.getDecimals()).toBigInteger(),
        Value.of(amount, coin),
        List.of(CoinDto.from(coin))
    );
  }

  public boolean hasValue() {
    return this.value.getValue().doubleValue() != 0.0;
  }
}
