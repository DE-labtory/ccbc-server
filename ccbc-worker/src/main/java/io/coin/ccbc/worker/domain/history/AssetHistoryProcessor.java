package io.coin.ccbc.worker.domain.history;

import io.coin.ccbc.domain.Account;
import io.coin.ccbc.domain.AddressCoinAssetFetcher;
import io.coin.ccbc.domain.Value;
import io.coin.ccbc.worker.domain.Processor;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AssetHistoryProcessor implements
    Processor<Account, AssetHistoryWritingItem> {

  private final AddressCoinAssetFetcher addressCoinAssetFetcher;

  public AssetHistoryProcessor(AddressCoinAssetFetcher addressCoinAssetFetcher) {
    this.addressCoinAssetFetcher = addressCoinAssetFetcher;
  }


  @Override
  public List<AssetHistoryWritingItem> process(List<Account> accounts,
      WorkerExecutionContext context) {

    LocalDateTime workingTime = (LocalDateTime) context.getOrThrow(
        "workingTime",
        () -> new IllegalArgumentException("workingTime does not exist")
    );

    return accounts.stream()
        .map(account -> {
              Value totalAssetValue = addressCoinAssetFetcher.getCoinAssets(account.getAddress())
                  .stream()
                  .map(coinAsset -> coinAsset.getTotalAsset().getValue())
                  .reduce(Value.zero(), Value::add);

              return AssetHistoryWritingItem.of(
                  account,
                  totalAssetValue,
                  workingTime
              );
            }
        ).collect(Collectors.toList());
  }
}