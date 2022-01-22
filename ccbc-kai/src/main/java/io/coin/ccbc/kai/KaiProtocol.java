package io.coin.ccbc.kai;

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

public class KaiProtocol implements Defi {

  public static String NAME = "Kai-Protocol";
  public static Address BOARDROOM_ADDRESS = Address.of(
      "0x931579fa23580cb2214fbe89ab487f6aede8a352");
  public static Address SKAI_ADDRESS = Address.of("0x37d46c6813b121d6a27ed263aef782081ae95434");
  public static Address BKAI_ADDRESS = Address.of("0x968d93a44b3ef61168ca621a59ddc0e56583e592");
  public static Address KAI_ADDRESS = Address.of("0xe950bdcfa4d1e45472e76cf967db93dbfc51ba3e");
  public static Address VKAI_ADDRESS = Address.of("0x44efe1ec288470276e29ac3adb632bff990e2e1f");
  public static Address USDT_ADDRESS = Address.of("0xcee8faf64bb97a73bb51e115aa89c17ffa8dd167");

  private final CoinRepository coinRepository;
  private final KaiProtocolClient kaiProtocolClient;

  public KaiProtocol(CoinRepository coinRepository, KaiProtocolClient kaiProtocolClient) {
    this.coinRepository = coinRepository;
    this.kaiProtocolClient = kaiProtocolClient;
  }

  @Override
  public List<Commodity> getAllCommodities() {
    return Collections.singletonList(
        this.getKaiCommodity()
    );
  }

  private Commodity getKaiCommodity() {
    Coin skai = coinRepository.findByAddressOrElseThrow(SKAI_ADDRESS);
    return new Commodity(
        "skai staking",
        BOARDROOM_ADDRESS,
        this.kaiProtocolClient.getApr(),
        this.kaiProtocolClient.getTvl() * this.coinRepository.findByAddressOrElseThrow(USDT_ADDRESS)
            .getPrice().getValue(),
        List.of(skai)
    );
  }

  @Override
  public List<InvestmentAsset> getAllInvestmentAssets(Address userAddress) {
    Amount amount = this.kaiProtocolClient.getStakedBalance(userAddress);
    Reward reward = this.kaiProtocolClient.getReward(userAddress);
    Coin skai = coinRepository.findByAddressOrElseThrow(SKAI_ADDRESS);
    Coin vkai = coinRepository.findByAddressOrElseThrow(VKAI_ADDRESS);
    Coin kai = coinRepository.findByAddressOrElseThrow(KAI_ADDRESS);

    Value receivableInterestValue = Value.of(
        reward.getKaiAmount(),
        kai.getPrice(),
        kai.getDecimals()
    ).add(Value.of(
        reward.getVkaiAmount(),
        vkai.getPrice(),
        vkai.getDecimals()
    ));

    return Collections.singletonList(
        new InvestmentAsset(
            this.getKaiCommodity(),
            receivableInterestValue,
            Value.zero(),
            Collections.singletonList(new Asset(
                skai,
                amount,
                Value.of(
                    amount,
                    skai.getPrice(),
                    skai.getDecimals()
                )
            ))
        )
    );
  }

  @Override
  public String getProtocolName() {
    return NAME;
  }

  @Override
  public DefiInformation getInformation() {
    return new DefiInformation(
        NAME,
        "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/kai.png",
        "#1F2729",
        this.kaiProtocolClient.getTotalTvl() * this.coinRepository.findByAddressOrElseThrow(
            USDT_ADDRESS).getPrice().getValue()
    );
  }

  @Override
  public List<io.coin.ccbc.domain.Reward> getRewards(Address address) {
    Reward reward = this.kaiProtocolClient.getReward(address);
    Coin vkai = coinRepository.findByAddressOrElseThrow(VKAI_ADDRESS);
    Coin kai = coinRepository.findByAddressOrElseThrow(KAI_ADDRESS);

    Value receivableInterestValue = Value.of(
        reward.getKaiAmount(),
        kai.getPrice(),
        kai.getDecimals()
    ).add(Value.of(
        reward.getVkaiAmount(),
        vkai.getPrice(),
        vkai.getDecimals()
    ));

    return Collections.singletonList(
        new io.coin.ccbc.domain.Reward(
            NAME,
            this.getKaiCommodity(),
            receivableInterestValue.getValue().doubleValue()
        )
    );
  }
}
