package io.coin.ccbc.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AddressCoinAssetFetcher {

  private final BalanceFetcher balanceFetcher;
  private final CoinRepository coinRepository;
  private final List<Defi> defis;

  public AddressCoinAssetFetcher(
      BalanceFetcher balanceFetcher,
      CoinRepository coinRepository,
      List<Defi> defis
  ) {
    this.balanceFetcher = balanceFetcher;
    this.coinRepository = coinRepository;
    this.defis = defis;
  }

  public List<CoinAsset> getCoinAssets(Address walletAddress) {
    List<Coin> allCoins = this.coinRepository.findAll();
    Map<Coin, CoinBalance> depositableBalanceMap = this.balanceFetcher.fetchCoinBalance(
            walletAddress,
            allCoins)
        .stream()
        .collect(Collectors.toMap(CoinBalance::getCoin, c -> c, CoinBalance::add));

    Map<Coin, CoinBalance> depositedBalanceMap = this.defis.stream()
        .flatMap(defi -> defi.getAllInvestmentAssets(walletAddress).stream())
        .flatMap(investmentAsset -> investmentAsset.getAssets().stream())
        .map(asset -> CoinBalance.of(asset.getCoin(), asset.getAmount()))
        .collect(Collectors.toMap(CoinBalance::getCoin, c -> c, CoinBalance::add));

    return allCoins.stream()
        .map(coin -> {
          Amount depositedAmount = depositedBalanceMap.getOrDefault(coin,
              CoinBalance.of(coin, Amount.zero())).getAmount();
          Amount depositableAmount = depositableBalanceMap.getOrDefault(coin,
              CoinBalance.of(coin, Amount.zero())).getAmount();
          Amount totalAmount = depositableAmount.add(depositedAmount);
          return new CoinAsset(
              coin,
              new Asset(coin, depositableAmount),
              new Asset(coin, totalAmount)
          );
        })
        //1원 미만 삭제
        .filter(
            coinAsset -> coinAsset.getTotalAsset().getValue().getValue().compareTo(BigDecimal.ONE) > 0
        ).collect(Collectors.toList());
  }
}
