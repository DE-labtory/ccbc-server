package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Coin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoinDto {

  private String id;
  private String symbol;
  private String name;
  private String address;
  private int decimals;
  private String symbolImageUrl;

  public static CoinDto from(Coin coin) {
    return new CoinDto(
        coin.getId(),
        coin.getSymbol(),
        coin.getName(),
        coin.getAddress().getValue(),
        coin.getDecimals(),
        coin.getSymbolImageUrl()
    );
  }
}
