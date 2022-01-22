package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Coin;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/20
 */

@Getter
@AllArgsConstructor
public class CoinPriceDto extends CoinDto {

  private final Double price;
  private final Double previousPrice; // before 24 hours

  public CoinPriceDto(Coin coin, Double previousPrice) {
    super(
        coin.getId(),
        coin.getSymbol(),
        coin.getName(),
        coin.getAddress().getValue(),
        coin.getDecimals(),
        coin.getSymbolImageUrl()
    );
    this.price = coin.getPrice().getValue();
    this.previousPrice = previousPrice;
  }

  public static CoinPriceDto of(
      Coin coin,
      Double previousPrice
  ) {
    return new CoinPriceDto(coin, previousPrice);
  }

}
