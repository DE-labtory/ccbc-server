package io.coin.ccbc.worker.domain;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.DexTradeFinder;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.Trade;
import io.coin.ccbc.support.BigIntegerConverter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

public class DexPriceProvider {
  private final DexTradeFinder dexTradeFinder;
  private final String baseCoinSymbol;
  private final CoinRepository coinRepository;

  public DexPriceProvider(
      String baseCoinSymbol,
      CoinRepository coinRepository,
      DexTradeFinder dexTradeFinder
  ) {
    this.coinRepository = coinRepository;
    this.baseCoinSymbol = baseCoinSymbol;
    this.dexTradeFinder = dexTradeFinder;
  }

  public Price getPrice(Coin coin) {
    // base coin price 를 base coin provider 로부터 받아온뒤
    Coin baseCoin = this.coinRepository.findBySymbol(this.baseCoinSymbol)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("'%s' is not exist in repository", this.baseCoinSymbol)));

    Price baseCoinPrice = baseCoin.getPrice();
    BigInteger baseCoinAmount = BigIntegerConverter
        .calculateByDecimals(BigInteger.ONE, baseCoin.getDecimals());

    // dex 에서의 최적 경로를 찾아온다
    List<Trade> trades = this.dexTradeFinder
        .getTradeExactAmountIn(baseCoin, coin, baseCoinAmount, 3);
    Trade bestTrade = trades.stream()
        .filter(trade -> trade.getToAmount().signum() != 0)
        .findFirst()
        .orElse(null);
    if (bestTrade == null) {
      return null;
    }
    // 최적경로를 이용해 원하는 코인과 base coin reserve 를 이용해 코인가격을 결정한다
    return Price.from(
        BigDecimal.valueOf(baseCoinPrice.getValue())
            .multiply(new BigDecimal(baseCoinAmount))
            .divide(new BigDecimal(BigIntegerConverter.calculateByDecimals(BigInteger.ONE,
                baseCoin.getDecimals())), 18, RoundingMode.HALF_UP)
            .multiply(new BigDecimal(BigIntegerConverter.calculateByDecimals(BigInteger.ONE,
                bestTrade.getToCoin().getDecimals())))
            .divide(new BigDecimal(bestTrade.getToAmount()), 18, RoundingMode.HALF_UP)
            .doubleValue()
    );
  }
}
