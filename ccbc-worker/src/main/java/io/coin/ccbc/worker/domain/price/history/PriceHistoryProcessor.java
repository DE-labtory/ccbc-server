package io.coin.ccbc.worker.domain.price.history;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.worker.domain.Processor;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.time.LocalDateTime;
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
public class PriceHistoryProcessor implements
    Processor<Coin, PriceHistoryWritingItem> {

  @Override
  public List<PriceHistoryWritingItem> process(List<Coin> coins,
      WorkerExecutionContext context) {
    LocalDateTime workingTime = (LocalDateTime) context.getOrThrow(
        "workingTime",
        () -> new IllegalArgumentException("workingTime does not exist")
    );
    return coins.stream()
        .map(coin -> PriceHistoryWritingItem.of(
            coin,
            workingTime
        ))
        .collect(Collectors.toList());
  }
}
