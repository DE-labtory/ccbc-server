package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.worker.domain.Reader;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Component
public class PriceHistoryReader implements Reader<Coin> {

  private final CoinRepository coinRepository;

  public PriceHistoryReader(final CoinRepository coinRepository) {
    this.coinRepository = coinRepository;
  }

  @Override
  public List<Coin> read(WorkerExecutionContext context) {
    return new ArrayList<>(this.coinRepository.getAllCoinMap().values());
  }
}
