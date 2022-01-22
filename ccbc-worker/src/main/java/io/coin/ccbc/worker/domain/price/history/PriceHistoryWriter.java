package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.domain.PriceHistoryRepository;
import io.coin.ccbc.worker.domain.Writer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Component
public class PriceHistoryWriter implements Writer<PriceHistoryWritingItem> {

  private final PriceHistoryRepository priceHistoryRepository;

  public PriceHistoryWriter(final PriceHistoryRepository priceHistoryRepository) {
    this.priceHistoryRepository = priceHistoryRepository;
  }

  @Override
  public void write(List<PriceHistoryWritingItem> items) {
    this.priceHistoryRepository.saveAll(
        items.stream()
            .map(item -> PriceHistory.collected(
                item.getCoin(),
                item.getCollectedAt()
            ))
            .collect(Collectors.toList())
    );
  }
}
