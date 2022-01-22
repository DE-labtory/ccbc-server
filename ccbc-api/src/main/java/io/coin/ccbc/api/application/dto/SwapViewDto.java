package io.coin.ccbc.api.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/18
 */
@Getter
@AllArgsConstructor
public class SwapViewDto extends SwapPriceDto {

  private Double swapRatio;

  private Double slippage;

  private Double priceImpact;

  private Double totalFeePercentage;

  private List<SwapPathDto> path;

  public SwapViewDto(
      SwapPriceDto swapPriceDto
  ) {
    super(
        swapPriceDto.getFromCoinPrice(),
        swapPriceDto.getFromCoinPreviousPrice(),
        swapPriceDto.getToCoinPrice(),
        swapPriceDto.getToCoinPreviousPrice()
    );
  }

  public SwapViewDto(
      SwapPriceDto swapPriceDto,
      Double swapRatio,
      Double slippage,
      Double priceImpact,
      Double totalFeePercentage,
      List<SwapPathDto> path
  ) {
    this(swapPriceDto);
    this.swapRatio = swapRatio;
    this.slippage = slippage;
    this.priceImpact = priceImpact;
    this.totalFeePercentage = totalFeePercentage;
    this.path = path;
  }

  public static SwapViewDto onlyPrice(
      SwapPriceDto swapPriceDto
  ) {
    return new SwapViewDto(swapPriceDto);
  }

  public static SwapViewDto of(
      SwapPriceDto swapPriceDto,
      Double swapRatio,
      Double slippage,
      Double priceImpact,
      Double totalFeePercentage,
      List<SwapPathDto> path
  ) {
    return new SwapViewDto(
        swapPriceDto,
        swapRatio,
        slippage,
        priceImpact,
        totalFeePercentage,
        path
    );
  }

}
