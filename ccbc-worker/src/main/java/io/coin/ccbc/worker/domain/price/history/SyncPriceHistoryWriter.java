package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.domain.PriceHistoryRepository;
import io.coin.ccbc.worker.domain.Writer;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SyncPriceHistoryWriter implements Writer<PriceHistory> {

  private final PriceHistoryRepository priceHistoryRepository;

  public SyncPriceHistoryWriter(PriceHistoryRepository priceHistoryRepository) {
    this.priceHistoryRepository = priceHistoryRepository;
  }

  @Override
  public void write(List<PriceHistory> priceHistories) {
    this.priceHistoryRepository.saveAll(priceHistories);
  }
}
