package io.coin.ccbc.worker.domain.history;

import io.coin.ccbc.domain.AssetHistory;
import io.coin.ccbc.domain.AssetHistoryRepository;
import io.coin.ccbc.worker.domain.Writer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AssetHistoryWriter implements Writer<AssetHistoryWritingItem> {

  private final AssetHistoryRepository assetHistoryRepository;

  public AssetHistoryWriter(AssetHistoryRepository assetHistoryRepository) {
    this.assetHistoryRepository = assetHistoryRepository;
  }

  @Override
  public void write(List<AssetHistoryWritingItem> items) {
    this.assetHistoryRepository.saveAll(
        items.stream()
            .map(item -> AssetHistory.collected(
                item.getAccount().getAddress(),
                item.getAssetValue(),
                item.getWorkingTime()
            ))
            .collect(Collectors.toList())
    );
  }
}
