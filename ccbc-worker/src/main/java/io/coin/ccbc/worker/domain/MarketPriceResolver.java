package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Price;
import org.springframework.lang.Nullable;

public class MarketPriceResolver {

  private final ExchangePriceProvider exchangePriceProvider;
  private final DexPriceProvider dexPriceProvider;

  public MarketPriceResolver(
      ExchangePriceProvider exchangePriceProvider,
      DexPriceProvider dexPriceProvider
  ) {
    this.exchangePriceProvider = exchangePriceProvider;
    this.dexPriceProvider = dexPriceProvider;
  }

  @Nullable
  public Price getPrice(Coin coin) {
    Price price;
    try {
      price = this.exchangePriceProvider.getPrice(coin);
    } catch (Exception e) {
      price = this.dexPriceProvider.getPrice(coin);
    }
    return price;
  }
}
