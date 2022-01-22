package io.coin.ccbc.klayfi.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Asset;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Commodity;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.domain.DefiInformation;
import io.coin.ccbc.domain.InvestmentAsset;
import io.coin.ccbc.domain.Value;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Klayfi implements Defi {

  public static Address USDT_ADDRESS = Address.of("0xcee8faf64bb97a73bb51e115aa89c17ffa8dd167");
  private static final String PROTOCOL_NAME = "KlayFi";
  private final KlayfiCommodityRepository klayfiCommodityRepository;
  private final KlayfiCrawler klayfiCrawler;
  private final KlayfiClient klayfiClient;
  private final CoinRepository coinRepository;

  public Klayfi(
      KlayfiCommodityRepository klayfiCommodityRepository,
      KlayfiCrawler klayfiCrawler,
      KlayfiClient klayfiClient,
      CoinRepository coinRepository
  ) {
    this.klayfiCommodityRepository = klayfiCommodityRepository;
    this.klayfiCrawler = klayfiCrawler;
    this.klayfiClient = klayfiClient;
    this.coinRepository = coinRepository;
  }

  @Override
  public List<Commodity> getAllCommodities() {
    List<KlayfiCommodity> klayfiCommodities = this.klayfiCommodityRepository.findAllWithPoolAndCoin();
    List<KlayfiCommodityInfo> klayfiCommodityInfos = this.klayfiCrawler.getAllAprs();
    double usdtPrice = this.coinRepository.findByAddressOrElseThrow(USDT_ADDRESS).getPrice().getValue();
    List<CompletableFuture<Commodity>> completableFutures = klayfiCommodities.stream()
        .map(klayfiCommodity -> CompletableFuture.supplyAsync(() -> {
          Commodity commodity = this.resolveCommodity(klayfiCommodity);
          KlayfiCommodityInfo commodityInfo = this.getMatchedCommodityInfo(klayfiCommodity, klayfiCommodityInfos);
          if (commodityInfo != null) {
            commodity.updateApr(commodityInfo.getApr());
            commodity.updateTvl(commodityInfo.getTvl() * usdtPrice);
          }
          return commodity;
        }))
        .collect(Collectors.toList());
    return completableFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }

  @Override
  public List<InvestmentAsset> getAllInvestmentAssets(Address userAddress) {
    List<KlayfiCommodity> klayfiCommodities = this.klayfiCommodityRepository.findAllWithPoolAndCoin();
    List<KlayfiCommodityInfo> klayfiCommodityInfos = this.klayfiCrawler.getAllAprs();
    List<CompletableFuture<InvestmentAsset>> completableFutures = klayfiCommodities.stream()
        .map(klayfiCommodity -> CompletableFuture.supplyAsync(() -> this.resolveInvestmentAsset(
            klayfiCommodity,
            this.getMatchedCommodityInfo(klayfiCommodity, klayfiCommodityInfos),
            userAddress
        )))
        .collect(Collectors.toList());
    return completableFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }

  @Override
  public String getProtocolName() {
    return PROTOCOL_NAME;
  }

  @Override
  public DefiInformation getInformation() {
    double usdtPrice = this.coinRepository.findByAddressOrElseThrow(USDT_ADDRESS).getPrice().getValue();
    return new DefiInformation(
        PROTOCOL_NAME,
        "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/klayfi.png",
        "#19140E",
        this.klayfiCrawler.getTvl() * usdtPrice
    );
  }

  private InvestmentAsset resolveInvestmentAsset(
      KlayfiCommodity klayfiCommodity,
      KlayfiCommodityInfo commodityInfo,
      Address targetAddress
  ) {
    Commodity commodity = this.resolveCommodity(klayfiCommodity);
    if (commodityInfo != null) {
      commodity.updateApr(commodityInfo.getApr());
      commodity.updateTvl(commodityInfo.getTvl());
    }

    Value receivableInterestValue;
    List<Asset> assets;
    switch (klayfiCommodity.getType()) {
      case CORE_POOL:
        StakingInfo stakingInfo = this.klayfiClient
            .getCorePoolInfo(klayfiCommodity.getAddress(), targetAddress);
        Coin stakedCoin = klayfiCommodity.getCoin();
        receivableInterestValue = Value
            .of(stakingInfo.getRewardBalance().getCoinAmount(), stakedCoin.getPrice(),
                stakedCoin.getDecimals());
        assets = Collections.singletonList(
            new Asset(
                stakedCoin,
                stakingInfo.getStakedBalance().getCoinAmount(),
                Value.of(
                    stakingInfo.getStakedBalance().getCoinAmount(),
                    stakedCoin.getPrice(),
                    stakedCoin.getDecimals()
                )
            )
        );
        break;
      case CORE_VAULT:
        VaultInfo vaultInfo = this.klayfiClient.getCoreVaultInfo(
            klayfiCommodity.getAddress(),
            targetAddress
        );
        Coin coin0 = klayfiCommodity
            .getMatchedCoin(vaultInfo.getVaultBalance().getCoin0Balance().getCoinAddress());
        Amount coin0Amount = vaultInfo.getVaultBalance().getCoin0Balance().getCoinAmount();
        Coin coin1 = klayfiCommodity
            .getMatchedCoin(vaultInfo.getVaultBalance().getCoin1Balance().getCoinAddress());
        Amount coin1Amount = vaultInfo.getVaultBalance().getCoin1Balance().getCoinAmount();
        receivableInterestValue = vaultInfo.getRewards()
            .stream()
            .map(reward -> {
              Coin coin = klayfiCommodity.getMatchedCoin(reward.getCoinAddress());
              return Value.of(reward.getCoinAmount(), coin.getPrice(), coin.getDecimals());
            })
            .reduce(Value.zero(), Value::add);
        assets = Arrays.asList(
            new Asset(
                coin0,
                coin0Amount,
                Value.of(
                    coin0Amount,
                    coin0.getPrice(),
                    coin0.getDecimals()
                )
            ),
            new Asset(
                coin1,
                coin1Amount,
                Value.of(
                    coin1Amount,
                    coin1.getPrice(),
                    coin1.getDecimals()
                )
            )
        );
        break;
      case GROWTH_VAULT:
      case PRIME_VAULT:
        vaultInfo = this.klayfiClient.getVaultInfo(
            klayfiCommodity.getAddress(),
            targetAddress
        );
        coin0 = klayfiCommodity
            .getMatchedCoin(vaultInfo.getVaultBalance().getCoin0Balance().getCoinAddress());
        coin0Amount = vaultInfo.getVaultBalance().getCoin0Balance().getCoinAmount();
        coin1 = klayfiCommodity
            .getMatchedCoin(vaultInfo.getVaultBalance().getCoin1Balance().getCoinAddress());
        coin1Amount = vaultInfo.getVaultBalance().getCoin1Balance().getCoinAmount();
        receivableInterestValue = vaultInfo.getRewards()
            .stream()
            .map(reward -> {
              Coin coin = klayfiCommodity.getMatchedCoin(reward.getCoinAddress());
              return Value.of(reward.getCoinAmount(), coin.getPrice(), coin.getDecimals());
            })
            .reduce(Value.zero(), Value::add);
        assets = Arrays.asList(
            new Asset(
                coin0,
                coin0Amount,
                Value.of(
                    coin0Amount,
                    coin0.getPrice(),
                    coin0.getDecimals()
                )
            ),
            new Asset(
                coin1,
                coin1Amount,
                Value.of(
                    coin1Amount,
                    coin1.getPrice(),
                    coin1.getDecimals()
                )
            )
        );
        break;
      default:
        throw new IllegalStateException(String
            .format("'%s' is not supported klayfi commodity type", klayfiCommodity.getType()));
    }

    return new InvestmentAsset(commodity, receivableInterestValue, Value.zero(), assets);
  }

  private Commodity resolveCommodity(KlayfiCommodity klayfiCommodity) {
    if (klayfiCommodity.isStaking()) {
      return Commodity.withoutApr(
          klayfiCommodity.getCoin().getName() + " Staking",
          klayfiCommodity.getAddress(),
          Collections.singletonList(klayfiCommodity.getCoin())
      );
    }

    return Commodity.withoutApr(
        klayfiCommodity.getPool().getCoin0().getName() + "-" + klayfiCommodity.getPool().getCoin1()
            .getName(),
        klayfiCommodity.getAddress(),
        klayfiCommodity.getPool().getCoins()
    );
  }

  private KlayfiCommodityInfo getMatchedCommodityInfo(KlayfiCommodity commodity, List<KlayfiCommodityInfo> klayfiCommodityInfos) {
    for (KlayfiCommodityInfo klayfiCommodityInfo : klayfiCommodityInfos) {
      if (commodity.isStaking() && !klayfiCommodityInfo.isStaking()) {
        continue;
      }

      if (klayfiCommodityInfo.isStaking() && commodity.isStaking()) {
        return klayfiCommodityInfo;
      }

      String coin0Symbol = commodity.getPool().getCoin0().getSymbol();
      String coin1Symbol = commodity.getPool().getCoin1().getSymbol();
      if (klayfiCommodityInfo.isMatchedPair(coin0Symbol, coin1Symbol)) {
        return klayfiCommodityInfo;
      }
    }
    return null;
  }

  @Override
  public List<io.coin.ccbc.domain.Reward> getRewards(Address address) {
    return this.getAllInvestmentAssets(address).stream()
        .map(investmentAsset -> new io.coin.ccbc.domain.Reward(
            PROTOCOL_NAME,
            investmentAsset.getCommodity(),
            investmentAsset.getReceivableInterestValue().getValue().doubleValue()
        ))
        .collect(Collectors.toList());
  }
}
