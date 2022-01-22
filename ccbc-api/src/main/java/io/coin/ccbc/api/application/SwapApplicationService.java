package io.coin.ccbc.api.application;

import io.coin.ccbc.api.application.dto.SwapInformationRequestParams;
import io.coin.ccbc.api.application.dto.SwapPathDto;
import io.coin.ccbc.api.application.dto.SwapPriceDto;
import io.coin.ccbc.api.application.dto.SwapViewDto;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.DexTradeFinder;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.PriceHistory;
import io.coin.ccbc.domain.PriceHistoryRepository;
import io.coin.ccbc.domain.Time;
import io.coin.ccbc.domain.Trade;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Bomi
 * @date 2021/08/18
 */

@Service
public class SwapApplicationService {

  private final int RECOMMENDED_SWAP_PATH_COUNT = 3;
  private final double FIXED_SLIPPAGE = 0.5;

  private final CoinRepository coinRepository;
  private final PriceHistoryRepository priceHistoryRepository;
  private final DexTradeFinder dexTradeFinder;

  public SwapApplicationService(
      final CoinRepository coinRepository,
      final PriceHistoryRepository priceHistoryRepository,
      final DexTradeFinder dexTradeFinder
  ) {
    this.coinRepository = coinRepository;
    this.priceHistoryRepository = priceHistoryRepository;
    this.dexTradeFinder = dexTradeFinder;
  }

  public SwapViewDto getSwapInformationWithFrom(SwapInformationRequestParams request) {
    return this.getSwapInformation(
        request,
        (fromCoin, toCoin, fromCoinPrice, toCoinPrice, maxPathNum) -> {
          // 교환할 코인의 수량 구하기
          BigInteger swapCoinAmount = this.getSwapCoinAmount(
              Optional.ofNullable(request.getAmount()),
              fromCoin,
              Optional.ofNullable(fromCoinPrice),
              request.getValue()
          );

          return this.dexTradeFinder
              .getTradeExactAmountIn(fromCoin, toCoin, swapCoinAmount, maxPathNum);
        });
  }

  public SwapViewDto getSwapInformationWithTo(SwapInformationRequestParams request) {
    return this.getSwapInformation(
        request,
        (fromCoin, toCoin, fromCoinPrice, toCoinPrice, maxPathNum) -> {
          // 교환할 코인의 수량 구하기
          BigInteger swapCoinAmount = this.getSwapCoinAmount(
              Optional.ofNullable(request.getAmount()),
              toCoin,
              Optional.ofNullable(toCoinPrice),
              request.getValue()
          );

          return this.dexTradeFinder
              .getTradeExactAmountOut(fromCoin, toCoin, swapCoinAmount, maxPathNum);
        });
  }

  private SwapViewDto getSwapInformation(
      SwapInformationRequestParams request,
      TradeProvider tradeProvider
  ) {
    this.checkRequiredField(request);

    Coin fromCoin = this.coinRepository.findByIdOrElseThrow(request.getFromCoinId());
    Coin toCoin = this.coinRepository.findByIdOrElseThrow(request.getToCoinId());

    LocalDateTime previousTime = Time.nearbyWorkingTime(Time.timeBefore24Hours());
    Map<Coin, Price> previousPriceMap = this.priceHistoryRepository
        .findAllByCoinInAndCollectedAtIn(
            List.of(fromCoin, toCoin), List.of(previousTime)
        )
        .stream()
        .collect(Collectors.toMap(PriceHistory::getCoin, PriceHistory::getPrice));
    Price previousFromCoinPrice = previousPriceMap.getOrDefault(fromCoin, null);
    Price previousToCoinPrice = previousPriceMap.getOrDefault(toCoin, null);

    SwapPriceDto swapPriceDto = SwapPriceDto.of(
        fromCoin.getPrice().getValue(),
        previousFromCoinPrice == null ? null : previousFromCoinPrice.getValue(),
        toCoin.getPrice().getValue(),
        previousToCoinPrice == null ? null : previousToCoinPrice.getValue()
    );
    if (request.getValue() == null && request.getAmount() == null) {
      return SwapViewDto.onlyPrice(swapPriceDto);
    }

    Trade bestTrade = tradeProvider
        .getCandidates(fromCoin, toCoin, previousFromCoinPrice, previousToCoinPrice,
            RECOMMENDED_SWAP_PATH_COUNT)
        .stream()
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            String.format(
                "Can not found swap path fromCoin - %s, toCoin - %s, maxSwapCount - %d",
                fromCoin.getSymbol(), toCoin.getSymbol(), RECOMMENDED_SWAP_PATH_COUNT)
        ));

    double swapRatio =
        bestTrade.getToAmount().doubleValue() / bestTrade.getFromAmount().doubleValue();

    List<SwapPathDto> swapPathDtos = bestTrade.getRoute()
        .stream()
        .map(SwapPathDto::from)
        .collect(Collectors.toList());

    Double totalFeePercentage = swapPathDtos.stream()
        .map(SwapPathDto::getFeePercentage)
        .reduce(Double::sum)
        .get();

    return SwapViewDto.of(
        swapPriceDto,
        swapRatio,
        FIXED_SLIPPAGE,
        bestTrade.getPriceImpact(),
        totalFeePercentage,
        swapPathDtos
    );
  }

  private void checkRequiredField(SwapInformationRequestParams request) {
    if (StringUtils.isEmpty(request.getAddress())
        || StringUtils.isEmpty(request.getFromCoinId())
        || StringUtils.isEmpty(request.getToCoinId())) {
      throw new IllegalArgumentException("The parameter should be non-null, but it is null");
    }
  }

  private BigInteger getSwapCoinAmount(
      Optional<Double> amount, Coin coin, Optional<Price> price, Double value
  ) {
    long coinAmount = (long) (
        amount.orElseGet(() ->
            price.map(p -> value / p.getValue())
                .orElse(0.0)
        ) * Math.pow(10, coin.getDecimals()));
    return BigInteger.valueOf(coinAmount);
  }

  interface TradeProvider {

    List<Trade> getCandidates(
        Coin fromCoin, Coin toCoin, Price fromCoinPrice, Price toCoinPrice, int maxPathNum
    );
  }

}
