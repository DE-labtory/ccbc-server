package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Value;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositedAssetDto {

  private double receivedInterestValue;

  private double receivableInterestValue;

  private List<PoolAssetDto> poolAssets;

  public static DepositedAssetDto of(
      Value receivedInterest,
      Value receivableInterest,
      List<PoolAssetDto> poolAssetDtos
  ) {
    return new DepositedAssetDto(
        receivedInterest.getValue().doubleValue(),
        receivableInterest.getValue().doubleValue(),
        poolAssetDtos
    );
  }
}
