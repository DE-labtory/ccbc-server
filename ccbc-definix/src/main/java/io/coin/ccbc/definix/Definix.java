package io.coin.ccbc.definix;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Definix implements Defi {

  public static String NAME = "Definix";

  private final DefinixClient definixClient;
  private final DefinixFarmRepository definixFarmRepository;
  private final DefinixAprCalculator aprCalculator;
  private final CoinRepository coinrepository;

  public Definix(
      DefinixClient definixClient,
      DefinixFarmRepository definixFarmRepository, DefinixAprCalculator aprCalculator,
      CoinRepository coinrepository
  ) {
    this.definixClient = definixClient;
    this.definixFarmRepository = definixFarmRepository;
    this.aprCalculator = aprCalculator;
    this.coinrepository = coinrepository;
  }

  @Override
  public List<Commodity> getAllCommodities() {
    List<DefinixFarm> allDefinixFarms = this.definixFarmRepository.findAll();
    Map<Address, FarmInfo> allPairInfoMap = definixClient.getAllFarmInfos(
            allDefinixFarms.stream().map(DefinixFarm::getPid).collect(Collectors.toList())
        )
        .stream()
        .collect(Collectors.toMap(
            FarmInfo::getFarmAddress,
            poolInfo -> poolInfo
        ));

    return allDefinixFarms.stream()
        .map(pair -> {
          Coin coin0 = pair.getCoin0();
          Coin coin1 = pair.getCoin1();
          FarmInfo poolInfo = allPairInfoMap.get(pair.getAddress());
          Value coin0Value = Value.of(
              Amount.of(poolInfo.getToken0Reserve()),
              coin0.getPrice(),
              coin0.getDecimals()
          );
          Value coin1Value = Value.of(
              Amount.of(poolInfo.getToken1Reserve()),
              coin1.getPrice(),
              coin1.getDecimals()
          );
          return new Commodity(
              pair.getName(),
              pair.getAddress(),
              aprCalculator.calculate(
                  poolInfo,
                  coin0,
                  coin1
              ).doubleValue(),
              coin0Value.add(coin1Value).getValue().doubleValue(),
              pair.getCoins()
          );
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<InvestmentAsset> getAllInvestmentAssets(Address userAddress) {
    Map<Address, DefinixFarm> pairMap = this.definixFarmRepository.getAllFarmMap();
    Map<Address, Commodity> commodityMap = this.getAllCommodities()
        .stream()
        .collect(Collectors.toMap(Commodity::getAddress, commodity -> commodity));
    Map<Address, Coin> coinMap = this.coinrepository.getAllCoinMap();

    return definixClient
        .getFarmBalances(userAddress,
            pairMap.values().stream().map(DefinixFarm::getPid).collect(Collectors.toList()))
        .stream()
        .filter(walletFarmBalance -> walletFarmBalance.getLpAmount().signum() > 0)
        .map(walletFarmBalance -> {
          Commodity commodity = commodityMap.get(walletFarmBalance.getFarmAddress());
          Value receivableValue = Value.zero();
          Value receivedValue = Value.zero();
          for (WalletFarmInterest walletFarmInterest : walletFarmBalance.getInterests()) {
            Coin coin = coinMap.get(walletFarmInterest.getCoinAddress());
            receivableValue = receivableValue.add(Value.of(
                walletFarmInterest.getReceivableAmount(),
                coin.getPrice(),
                coin.getDecimals()
            ));
            receivedValue = receivedValue.add(Value.of(
                walletFarmInterest.getReceivedAmount(),
                coin.getPrice(),
                coin.getDecimals()
            ));
          }

          DefinixFarm definixFarm = pairMap.get(walletFarmBalance.getFarmAddress());
          Asset coin0Asset = new Asset(
              definixFarm.getCoin0(),
              Amount.of(walletFarmBalance.getToken0Balance()),
              Value.of(
                  Amount.of(walletFarmBalance.getToken0Balance()),
                  definixFarm.getCoin0().getPrice(),
                  definixFarm.getCoin0().getDecimals()
              )
          );
          Asset coin1Asset = new Asset(
              definixFarm.getCoin1(),
              Amount.of(walletFarmBalance.getToken1Balance()),
              Value.of(
                  Amount.of(walletFarmBalance.getToken1Balance()),
                  definixFarm.getCoin1().getPrice(),
                  definixFarm.getCoin1().getDecimals()
              )
          );
          return new InvestmentAsset(
              commodity,
              receivableValue,
              receivedValue,
              List.of(coin0Asset, coin1Asset)
          );
        })
        .filter(investmentAsset -> investmentAsset.getTotalAssetValue().getValue().signum() != 0)
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
        "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/finix.png",
        "3E86C4",
        getAllCommodities().stream()
            .map(commodity -> commodity.getTvl())
            .reduce(0.0, Double::sum)
    );
  }

  @Override
  public List<Reward> getRewards(Address address) {
    Map<Address, DefinixFarm> pairMap = this.definixFarmRepository.getAllFarmMap();
    List<WalletFarmBalance> balanceList = definixClient
        .getFarmBalances(address,
            pairMap.values().stream().map(DefinixFarm::getPid).collect(Collectors.toList()));
    Map<Address, Commodity> commodityMap = this.getAllCommodities()
        .stream()
        .collect(Collectors.toMap(Commodity::getAddress, commodity -> commodity));
    Map<Address, Coin> coinMap = this.coinrepository.getAllCoinMap();
    return balanceList
        .stream()
        .map(walletFarmBalance -> CompletableFuture.supplyAsync(() -> {
          Commodity commodity = commodityMap.get(walletFarmBalance.getFarmAddress());
          Value receivableValue = Value.zero();
          for (WalletFarmInterest walletFarmInterest : walletFarmBalance.getInterests()) {
            Coin coin = coinMap.get(walletFarmInterest.getCoinAddress());
            receivableValue = receivableValue.add(Value.of(
                walletFarmInterest.getReceivableAmount(),
                coin.getPrice(),
                coin.getDecimals()
            ));
          }
          return new Reward(NAME, commodity, receivableValue.getValue().doubleValue());
        })).collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .filter(reward -> reward.getCommodity() != null)
        .collect(Collectors.toList());
  }
}
