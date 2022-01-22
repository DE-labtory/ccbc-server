package io.coin.ccbc.klayswap;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Asset;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Commodity;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.domain.DefiInformation;
import io.coin.ccbc.domain.InvestmentAsset;
import io.coin.ccbc.domain.Reward;
import io.coin.ccbc.domain.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
public class Klayswap implements Defi {

  public static String NAME = "KlaySwap";

  private final KlayswapClient klayswapClient;
  private final KlayswapPoolRepository klayswapPoolRepository;
  private final CoinRepository coinrepository;
  private final KlayswapCommodityFetcher klayswapCommodityFetcher;

  public Klayswap(
      KlayswapClient klayswapClient,
      KlayswapPoolRepository klayswapPoolRepository,
      CoinRepository coinrepository,
      KlayswapCommodityFetcher klayswapCommodityFetcher
  ) {
    this.klayswapClient = klayswapClient;
    this.klayswapPoolRepository = klayswapPoolRepository;
    this.coinrepository = coinrepository;
    this.klayswapCommodityFetcher = klayswapCommodityFetcher;
  }

  @Override
  public List<Commodity> getAllCommodities() {
    return this.klayswapCommodityFetcher.getAllCommodities();
  }

  @Override
  public List<InvestmentAsset> getAllInvestmentAssets(Address userAddress) {
    Map<Address, KlayswapPool> poolMap = this.klayswapPoolRepository.getAllPoolMap();
    Map<Address, Commodity> commodityMap = this.getAllCommodities()
        .stream()
        .collect(Collectors.toMap(Commodity::getAddress, commodity -> commodity));
    Map<Address, Coin> coinMap = this.coinrepository.getAllCoinMap();
    List<WalletPoolBalance> balanceList = klayswapClient
        .getPoolBalances(new ArrayList<>(poolMap.keySet()), userAddress);
    return balanceList
        .stream()
        .map(walletPoolBalance -> {
          Commodity commodity = commodityMap.get(walletPoolBalance.getPoolAddress());
          Value receivableValue = Value.zero();
          Value receivedValue = Value.zero();
          for (WalletPoolInterest walletPoolInterest : walletPoolBalance.getInterests()) {
            Coin coin = coinMap.get(walletPoolInterest.getCoinAddress());
            if (coin == null) {
              continue;
            }

            receivableValue = receivableValue.add(Value.of(
                walletPoolInterest.getReceivableAmount(),
                coin.getPrice(),
                coin.getDecimals()
            ));
            receivedValue = receivedValue.add(Value.of(
                walletPoolInterest.getReceivedAmount(),
                coin.getPrice(),
                coin.getDecimals()
            ));
          }
          KlayswapPool klayswapPool = poolMap.get(walletPoolBalance.getPoolAddress());
          Asset coin0Asset = new Asset(
              klayswapPool.getCoin0(),
              Amount.of(walletPoolBalance.getToken0Balance()),
              Value.of(
                  Amount.of(walletPoolBalance.getToken0Balance()),
                  klayswapPool.getCoin0().getPrice(),
                  klayswapPool.getCoin0().getDecimals()
              )
          );
          Asset coin1Asset = new Asset(
              klayswapPool.getCoin1(),
              Amount.of(walletPoolBalance.getToken1Balance()),
              Value.of(
                  Amount.of(walletPoolBalance.getToken1Balance()),
                  klayswapPool.getCoin1().getPrice(),
                  klayswapPool.getCoin1().getDecimals()
              )
          );
          return new InvestmentAsset(
              commodity,
              receivableValue,
              receivedValue,
              List.of(
                  coin0Asset, coin1Asset
              )
          );
        })
        .filter(investmentAsset -> investmentAsset.getTotalAssetValue().getValue().signum() != 0)
        .collect(Collectors.toList());
  }

  @Override
  public List<Reward> getRewards(Address address) {
    Map<Address, KlayswapPool> poolMap = this.klayswapPoolRepository.getAllPoolMap();
    List<WalletPoolBalance> balanceList = klayswapClient
        .getPoolBalances(new ArrayList<>(poolMap.keySet()), address);
    Map<Address, Commodity> commodityMap = this.getAllCommodities()
        .stream()
        .collect(Collectors.toMap(Commodity::getAddress, commodity -> commodity));
    Map<Address, Coin> coinMap = this.coinrepository.getAllCoinMap();
    return balanceList
        .stream()
        .map(walletPoolBalance -> CompletableFuture.supplyAsync(() -> {
          Commodity commodity = commodityMap.get(walletPoolBalance.getPoolAddress());
          Value receivableValue = Value.zero();
          for (WalletPoolInterest walletPoolInterest : walletPoolBalance.getInterests()) {
            Coin coin = coinMap.get(walletPoolInterest.getCoinAddress());
            receivableValue = receivableValue.add(Value.of(
                walletPoolInterest.getReceivableAmount(),
                coin.getPrice(),
                coin.getDecimals()
            ));
          }
          return new Reward(NAME, commodity, receivableValue.getValue().doubleValue());
        })).collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }


  @Override
  public String getProtocolName() {
    return NAME;
  }

  @Override
  public DefiInformation getInformation() {

    return new DefiInformation(
        NAME,
        "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/klayswap.png",
        "F19A4A",
        getAllCommodities().stream()
            .map(Commodity::getTvl)
            .reduce(0.0, Double::sum)
    );
  }
}
