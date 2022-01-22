package io.coin.ccbc.worker.domain.price;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.worker.domain.MarketPriceResolver;
import io.coin.ccbc.worker.domain.Processor;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Slf4j
@Component
public class PriceProcessor implements
    Processor<Coin, Coin> {

  private final MarketPriceResolver marketPriceResolver;

  public PriceProcessor(
      MarketPriceResolver marketPriceResolver
  ) {
    this.marketPriceResolver = marketPriceResolver;
  }

  @Override
  public List<Coin> process(List<Coin> coins, WorkerExecutionContext context) {
    return coins.stream()
        .map(coin -> {
          Price price = this.marketPriceResolver.getPrice(coin);
          if (price == null) {
            coin.updatePrice(Price.zero());
            return coin;
          }
          coin.updatePrice(price);
          return coin;
        })
        .collect(Collectors.toList());
  }
}
