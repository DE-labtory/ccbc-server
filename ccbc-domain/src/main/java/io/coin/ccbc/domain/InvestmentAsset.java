package io.coin.ccbc.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class InvestmentAsset {

  private Commodity commodity;
  private Value receivableInterestValue;
  private Value receivedInterestValue;
  private List<Asset> assets;

  public InvestmentAsset(Commodity commodity, Value receivableInterestValue,
      Value receivedInterestValue, List<Asset> assets) {
    this.commodity = commodity;
    this.receivableInterestValue = receivableInterestValue;
    this.receivedInterestValue = receivedInterestValue;
    this.assets = assets;
  }

  public Value getTotalAssetValue() {
    return assets.stream().map(Asset::getValue).reduce(Value.zero(), Value::add);
  }
}
