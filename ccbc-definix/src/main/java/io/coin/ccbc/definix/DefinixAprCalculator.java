package io.coin.ccbc.definix;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Value;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefinixAprCalculator {

  private final CoinRepository coinRepository;

  public DefinixAprCalculator(
      CoinRepository coinRepository
  ) {
    this.coinRepository = coinRepository;
  }

  public BigDecimal calculate(
      FarmInfo farmInfo,
      Coin coin0,
      Coin coin1
  ) {
    // token 0 의 총 금액
    Value token0Value = Value.of(
        Amount.of(farmInfo.getToken0Reserve()),
        coin0.getPrice(),
        coin0.getDecimals()
    );

    // token 1 의 총 금액
    Value token1Value = Value.of(
        Amount.of(farmInfo.getToken1Reserve()),
        coin1.getPrice(),
        coin1.getDecimals()
    );
    // interest 의 총 금액
    Map<Address, Coin> allCoinMap = coinRepository.getAllCoinMap();
    Value interestValue = farmInfo.getInterestsPerBlock()
        .entrySet()
        .stream()
        .map(entry -> {
          Address interestCoinAddress = entry.getKey();
          Amount interestCoinPerBlock = entry.getValue();
          Coin coin = allCoinMap.get(interestCoinAddress);
          if (coin == null) {
            return Value.zero();
          }
          return Value.of(interestCoinPerBlock, coin.getPrice(), coin.getDecimals());
        }).reduce(Value.zero(), Value::add);

    // 365일 기준
    BigDecimal totalInterestValue = interestValue
        .getValue()
        .multiply(BigDecimal.valueOf(60 * 60 * 24 * 365));

    if (token0Value.add(token1Value).getValue().signum() == 0) {
      return BigDecimal.ZERO;
    }
    // apr = interest 의 총 금액 / (token 0 의 총 금액 + token 1 의 총 금액)
    return totalInterestValue
        .divide(token0Value.add(token1Value).getValue(), 18, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));
  }

}
