package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AssetAssembler {

  public Value assembleAssets(Address targetAddress, List<Coin> coins,
      List<Defi> defis) {
//    Map<Coin, Amount> coinAmountMap = this.getCoinAmountMap(targetAddress, klayswapPools, coins);
//    return coinAmountMap.keySet()
//        .stream()
//        .map(coin -> {
//          Amount coinAmount = coinAmountMap.get(coin);
//          Price coinPrice = coin.getPrice();
//          return Value.of(coinAmount, coinPrice, coin.getDecimals());
//        })
//        .reduce(Value.zero(), Value::add);
    return null;
  }
}
