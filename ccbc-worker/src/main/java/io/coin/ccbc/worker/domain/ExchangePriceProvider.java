package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.PriceProvider;
import io.coin.ccbc.domain.exceptions.ExchangeServerException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Bomi
 * @date 2021/08/13
 */
@Slf4j
public class ExchangePriceProvider {

  private final List<PriceProvider> priceProviders;

  public ExchangePriceProvider(
      final List<PriceProvider> priceProviders
  ) {
    this.priceProviders = priceProviders;
  }

  @Cacheable(value = "exchange_price_cache", key = "#coin.symbol", condition = "#coin.symbol.equals('KLAY')")
  public Price getPrice(Coin coin) {
    for (PriceProvider priceProvider : this.priceProviders) {
      try {
        return priceProvider.getPrice(coin);
      } catch (ExchangeServerException e) {
        log.error(e.getMessage());
      }
    }
    throw new ExchangeServerException("last fallback exchange's server is wrong");
  }

}
