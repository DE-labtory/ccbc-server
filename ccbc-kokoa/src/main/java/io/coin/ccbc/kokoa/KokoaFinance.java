package io.coin.ccbc.kokoa;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class KokoaFinance implements Defi {

  public static String PROTOCOL_NAME = "Kokoa-Finance";
  private static final String BORROW = "Borrow";
  private static final String GOVERN = "Govern";
  private static final String EARN = "Earn";
  public static final Address USDT_ADDRESS = Address
      .of("0xcee8faf64bb97a73bb51e115aa89c17ffa8dd167");
  public static final Address KLAY_ADDRESS = Address
      .of("0x0000000000000000000000000000000000000000");
  public static final Address AKLAY_ADDRESS = Address
      .of("0x74BA03198FEd2b15a51AF242b9c63Faf3C8f4D34");
  public static final Address KOKOA_ADDRESS = Address
      .of("0xb15183d0d4d5e86ba702ce9bb7b633376e7db29f");
  public static final Address KSD_ADDRESS = Address
      .of("0x4fa62f1f404188ce860c8f0041d6ac3765a72e67");

  private KoKoaFinanceClient koKoaFinanceClient;
  private CoinRepository coinRepository;

  public KokoaFinance(KoKoaFinanceClient koKoaFinanceClient,
      CoinRepository coinRepository) {
    this.koKoaFinanceClient = koKoaFinanceClient;
    this.coinRepository = coinRepository;
  }

  @Override
  public List<Commodity> getAllCommodities() {
    Map<Address, Coin> coinsByAddress = this.coinRepository.getAllCoinMap();
    Map<String, Coin> coinsBySymbol = coinsByAddress.entrySet()
        .stream()
        .collect(
            Collectors.toMap(
                e -> e.getValue().getSymbol().toUpperCase(),
                Entry::getValue,
                (e1, e2) -> e1 // todo fix to find address
            )
        );
    Coin klay = coinsByAddress.get(KLAY_ADDRESS);
    Coin aklay = coinsByAddress.get(AKLAY_ADDRESS);
    Coin kokoa = coinsByAddress.get(KOKOA_ADDRESS);
    Coin ksd = coinsByAddress.get(KSD_ADDRESS);
    Coin usdt = coinsByAddress.get(USDT_ADDRESS);

    KokoaStatus kokoaStatus = this.koKoaFinanceClient.getStatus();
    Commodity borrow = new Commodity(
        BORROW,
        Address.empty(),
        kokoaStatus.getBorrow().getAPR(),
        kokoaStatus.getBorrow().getVolume() * usdt.getPrice().getValue(),
        List.of(
            klay,
            aklay
        )
    );

    Commodity earn = new Commodity(
        EARN,
        Address.empty(),
        kokoaStatus.getEarn().getAPR(),
        kokoaStatus.getEarn().getVolume() * usdt.getPrice().getValue(),
        List.of(
            ksd
        )
    );

    Commodity govern = new Commodity(
        GOVERN,
        Address.empty(),
        kokoaStatus.getGovern().getAPR(),
        kokoaStatus.getGovern().getVolume() * usdt.getPrice().getValue(),
        List.of(
            kokoa
        )
    );

    List<Commodity> commodities = kokoaStatus
        .getFarms()
        .stream()
        .filter(farm -> coinsBySymbol.containsKey(farm.getTokenA().toUpperCase()))
        .filter(farm -> coinsBySymbol.containsKey(farm.getTokenB().toUpperCase()))
        .map(farm -> new Commodity(
            farm.getName(),
            Address.of(farm.getAddress()),
            farm.getAPR(),
            farm.getLpTokenPrice() * farm.getLpTokenTotalSupply() * usdt.getPrice().getValue(),
            List.of(
                coinsBySymbol.get(farm.getTokenA().toUpperCase()),
                coinsBySymbol.get(farm.getTokenB().toUpperCase())
            )
        )).collect(Collectors.toList());

    commodities.addAll(List.of(
        earn,
        govern,
        borrow
    ));
    return commodities;
  }

  @Override
  public List<InvestmentAsset> getAllInvestmentAssets(Address userAddress) {
    List<Commodity> kokoaCommodities = this.getAllCommodities();
    List<InvestmentAsset> investmentAssets = new ArrayList<>();
    Coin kokoa = this.coinRepository.findByAddressOrElseThrow(KOKOA_ADDRESS);
    Coin ksd = this.coinRepository.findByAddressOrElseThrow(KSD_ADDRESS);
    KokoaBalance kokoaBalance = this.koKoaFinanceClient.getBalance(userAddress);
    // borrow
    if (kokoaBalance.getBorrowInfo().isBorrowed()) {
      Commodity borrowCommodity = kokoaCommodities.stream()
          .filter(commodity -> commodity.getName().equalsIgnoreCase(BORROW)).findFirst()
          .orElseThrow(() -> new RuntimeException("there is no borrow commodity in kokoa finance"));
      Value receivableInterestValue = Value.of(
          Amount.of(kokoaBalance.getBorrowInfo().getReward()),
          kokoa.getPrice(),
          kokoa.getDecimals()
      );
      List<Asset> assets = kokoaBalance.getBorrowInfo().getCollateralInfos()
          .stream()
          .filter(collateralInfo -> borrowCommodity.getRelatedCoin()
                  .stream()
                  .anyMatch(relatedCoin -> relatedCoin.getSymbol()
                          .equalsIgnoreCase(collateralInfo.getCollateralType()))
          )
          .map(collateralInfo -> {
            Coin coin = borrowCommodity.getRelatedCoin()
                .stream()
                .filter(relatedCoin -> relatedCoin.getSymbol()
                    .equalsIgnoreCase(collateralInfo.getCollateralType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String
                    .format("there is no collateral coin '%s' in kokoa finance borrow commodity",
                        collateralInfo.getCollateralType())));

            return new Asset(coin, Amount.of(collateralInfo.getLoan()));
          })
          .collect(Collectors.toList());

      investmentAssets.add(
          new InvestmentAsset(
              borrowCommodity,
              receivableInterestValue,
              Value.zero(),
              assets
          )
      );
    }

    // govern
    if (kokoaBalance.getGovernInfo().isGoverned()) {
      Commodity governCommodity = kokoaCommodities.stream()
          .filter(commodity -> commodity.getName().equalsIgnoreCase(GOVERN)).findFirst()
          .orElseThrow(() -> new RuntimeException("there is no govern commodity in kokoa finance"));
      Value governKokoaReward = Value.of(
          Amount.of(kokoaBalance.getGovernInfo().getKokoaReward()),
          kokoa.getPrice(),
          kokoa.getDecimals()
      );
      Value governKsdReward = Value.of(
          Amount.of(kokoaBalance.getGovernInfo().getKsdReward()),
          ksd.getPrice(),
          ksd.getDecimals()
      );
      investmentAssets.add(
          new InvestmentAsset(
              governCommodity,
              governKokoaReward.add(governKsdReward),
              Value.zero(),
              Collections.singletonList(new Asset(
                  kokoa, // actually, sKOKOA
                  Amount.of(kokoaBalance.getGovernInfo().getStakedKokoaBalance())
              ))
          )
      );
    }

    // earn
    if (kokoaBalance.getEarnInfo().isEarned()) {
      Commodity earnCommodity = kokoaCommodities.stream()
          .filter(commodity -> commodity.getName().equalsIgnoreCase(EARN)).findFirst()
          .orElseThrow(() -> new RuntimeException("there is no earn commodity in kokoa finance"));

      investmentAssets.add(
          new InvestmentAsset(
              earnCommodity,
              Value.zero(),
              Value.zero(),
              Collections.singletonList(new Asset(
                  ksd,
                  Amount.of(kokoaBalance.getEarnInfo().getDepositedKsdBalance())
              ))
          )
      );
    }

    // farms
    investmentAssets.addAll(
        kokoaBalance.getFarmInfos()
            .stream()
            .map(farmInfo -> {
              Commodity farmCommodity = kokoaCommodities.stream()
                  .filter(commodity -> commodity.getName().equalsIgnoreCase(farmInfo.getName()))
                  .findFirst()
                  .orElseThrow(() -> new RuntimeException(String
                      .format("there is no '%s' farm commodity in kokoa finance",
                          farmInfo.getName())));
              Coin tokenA = farmCommodity.getRelatedCoin().stream()
                  .filter(
                      relatedCoin -> relatedCoin.getSymbol().equalsIgnoreCase(farmInfo.getTokenA()))
                  .findFirst().orElseThrow(() -> new RuntimeException(
                      String.format(
                          "there is no coin '%s' in '%s' farm in kokoa finance",
                          farmInfo.getTokenA(),
                          farmInfo.getName()
                      )));
              Coin tokenB = farmCommodity.getRelatedCoin().stream()
                  .filter(
                      relatedCoin -> relatedCoin.getSymbol().equalsIgnoreCase(farmInfo.getTokenB()))
                  .findFirst().orElseThrow(() -> new RuntimeException(
                      String.format(
                          "there is no coin '%s' in '%s' farm in kokoa finance",
                          farmInfo.getTokenB(),
                          farmInfo.getName()
                      )
                  ));

              return new InvestmentAsset(
                  farmCommodity,
                  Value.of(farmInfo.getKokoaReward(), kokoa.getPrice(), kokoa.getDecimals()),
                  Value.zero(),
                  Arrays.asList(
                      new Asset(tokenA, farmInfo.getTokenAamount()),
                      new Asset(tokenB, farmInfo.getTokenBamount())
                  )
              );
            })
            .collect(Collectors.toList())
    );

    return investmentAssets;
  }

  @Override
  public String getProtocolName() {
    return PROTOCOL_NAME;
  }

  @Override
  public DefiInformation getInformation() {
    Coin usdt = this.coinRepository.findByAddressOrElseThrow(USDT_ADDRESS);
    return new DefiInformation(
        PROTOCOL_NAME,
        "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/kokoa.png",
        "B8AA9F",
        this.koKoaFinanceClient.getStatus().getTVL() * usdt.getPrice().getValue()
    );
  }

  @Override
  public List<io.coin.ccbc.domain.Reward> getRewards(Address address) {
    return this.getAllInvestmentAssets(address).stream()
        .map(investmentAsset -> new Reward(
            PROTOCOL_NAME,
            investmentAsset.getCommodity(),
            investmentAsset.getReceivableInterestValue().getValue().doubleValue()
        ))
        .collect(Collectors.toList());
  }
}
