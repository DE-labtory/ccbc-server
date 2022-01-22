package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Exchange;
import io.coin.ccbc.domain.Price;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangePrice {

  private Coin coin;
  private Price price;
  private Exchange exchange;

  public static ExchangePrice of(
      Coin coin,
      Price price,
      Exchange exchange
  ) {
    return new ExchangePrice(coin, price, exchange);
  }
}
