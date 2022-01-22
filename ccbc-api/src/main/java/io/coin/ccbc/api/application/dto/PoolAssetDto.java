package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Value;
import io.coin.ccbc.klayswap.KlayswapPool;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PoolAssetDto {

  private String id;
  private String address;
  private Double apr;
  private CoinDto coin0;
  private CoinDto coin1;
  private Double receivableInterestValue;
  private Double assetValue;

  public static PoolAssetDto of(
      KlayswapPool klayswapPool,
      Double apy,
      Value receivableInterest,
      Value assetValue
  ) {
    return new PoolAssetDto(
        klayswapPool.getId(),
        klayswapPool.getAddress().getValue(),
        apy,
        CoinDto.from(klayswapPool.getCoin0()),
        CoinDto.from(klayswapPool.getCoin1()),
        receivableInterest.getValue().doubleValue(),
        assetValue.getValue().doubleValue()
    );
  }
}