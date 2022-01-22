package io.coin.ccbc.domain;

import org.springframework.lang.Nullable;

public interface PriceProvider {

  @Nullable
  Price getPrice(Coin coin);
}
