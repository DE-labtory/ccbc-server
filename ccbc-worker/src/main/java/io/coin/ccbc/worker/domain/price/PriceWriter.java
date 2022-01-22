package io.coin.ccbc.worker.domain.price;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.worker.domain.Writer;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Component
public class PriceWriter implements Writer<Coin> {

  private final CoinRepository coinRepository;

  public PriceWriter(final CoinRepository coinRepository) {
    this.coinRepository = coinRepository;
  }

  @Override
  public void write(List<Coin> coins) {
    this.coinRepository.saveAll(coins);
  }
}
