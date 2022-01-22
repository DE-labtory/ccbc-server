package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Value;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SummaryDto {

  private final double totalAssetValue;

  private final double depositedAssetValue;

  private final double depositableAssetValue;

  private final double receivableInterestValue;

  public static SummaryDto of(
      Value totalAssetValue,
      Value depositedAssetValue,
      Value depositableAssetValue,
      Value receivableInterestValue
  ) {
    return new SummaryDto(
        totalAssetValue.getValue().doubleValue(),
        depositedAssetValue.getValue().doubleValue(),
        depositableAssetValue.getValue().doubleValue(),
        receivableInterestValue.getValue().doubleValue()
    );
  }
}
