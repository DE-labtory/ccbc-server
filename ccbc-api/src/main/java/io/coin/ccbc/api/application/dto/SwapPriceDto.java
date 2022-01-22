package io.coin.ccbc.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Bomi
 * @date 2021/08/27
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SwapPriceDto {

  private Double fromCoinPrice;

  private Double fromCoinPreviousPrice; // before 24 hours

  private Double toCoinPrice;

  private Double toCoinPreviousPrice; // before 24 hours

  public static SwapPriceDto of(
      Double fromCoinPrice,
      Double fromCoinPreviousPrice,
      Double toCoinPrice,
      Double toCoinPreviousPrice
  ) {
    return new SwapPriceDto(
        fromCoinPrice,
        fromCoinPreviousPrice,
        toCoinPrice,
        toCoinPreviousPrice
    );
  }

}
