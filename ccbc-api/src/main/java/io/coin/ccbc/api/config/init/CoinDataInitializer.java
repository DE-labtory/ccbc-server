package io.coin.ccbc.api.config.init;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinDataInitializer {

  public static final Address KLAY_ADDRESS = Address
      .of("0x0000000000000000000000000000000000000000");
  public static final Address KSP_ADDRESS = Address
      .of("0xc6a2ad8cc6e4a7e08fc37cc5954be07d499e7654");
  public static final Address KETH_ADDRESS = Address
      .of("0x34d21b1e550d73cee41151c77f3c73359527a396");
  public static final Address KWBTC_ADDRESS = Address
      .of("0x16d0e1fbd024c600ca0380a4c5d57ee7a2ecbf9c");
  public static final Address KUSDT_ADDRESS = Address
      .of("0xcee8faf64bb97a73bb51e115aa89c17ffa8dd167");
  public static final Address KDAI_ADDRESS = Address
      .of("0x5c74070fdea071359b86082bd9f9b3deaafbe32b");
  public static final Address SKLAY_ADDRESS = Address
      .of("0xa323d7386b671e8799dca3582d6658fdcdcd940a");
  public static final Address KORC_ADDRESS = Address
      .of("0xfe41102f325deaa9f303fdd9484eb5911a7ba557");
  public static final Address KXRP_ADDRESS = Address
      .of("0x9eaefb09fe4aabfbe6b1ca316a3c36afc83a393f");
  public static final Address sKAI_ADDRESS = Address
      .of("0x37d46c6813b121d6a27ed263aef782081ae95434");
  public static final Address ABL_ADDRESS = Address
      .of("0x46f307b58bf05ff089ba23799fae0e518557f87c");
  public static final Address KBNB_ADDRESS = Address
      .of("0x574e9c26bda8b95d7329505b4657103710eb32ea");
  public static final Address KBELT_ADDRESS = Address
      .of("0xdfe180e288158231ffa5faf183eca3301344a51f");
  public static final Address FINIX_ADDRESS = Address
      .of("0xD51C337147c8033a43F3B5ce0023382320C113Aa");
  public static final Address KFI_ADDRESS = Address
      .of("0xDB116E2Dc96B4e69e3544f41b50550436579979a");
  public static final Address AKLAY_ADDRESS = Address
      .of("0x74BA03198FEd2b15a51AF242b9c63Faf3C8f4D34");
  public static final Address KOKOA_ADDRESS = Address
      .of("0xb15183d0d4d5e86ba702ce9bb7b633376e7db29f");
  public static final Address KSD_ADDRESS = Address
      .of("0x4fa62f1f404188ce860c8f0041d6ac3765a72e67");
  public static final Address HOUSE_ADDRESS = Address
      .of("0x158beff8c8cdebd64654add5f6a1d9937e73536c");
  public static final Address WOOD_ADDRESS = Address
      .of("0x7b7363cf78662b638a87f63871c302be363ddc7a");
  public static final Address KUSDC_ADDRESS = Address
      .of("0x754288077d0ff82af7a5317c7cb8c444d421d103");
  public static final Address WEMIX_ADDRESS = Address
      .of("0x5096db80b21ef45230c9e423c373f1fc9c0198dd");
  public static final Address SKAI_ADDRESS = Address
      .of("0x37d46c6813b121d6a27ed263aef782081ae95434");
  public static final Address KRONOS_ADDRESS = Address
          .of("0xd676e57ca65b827feb112ad81ff738e7b6c1048d");

  public static Address BKAI_ADDRESS = Address.of("0x968d93a44b3ef61168ca621a59ddc0e56583e592");
  public static Address KAI_ADDRESS = Address.of("0xe950bdcfa4d1e45472e76cf967db93dbfc51ba3e");
  public static Address VKAI_ADDRESS = Address.of("0x44efe1ec288470276e29ac3adb632bff990e2e1f");
  private final CoinRepository coinRepository;
  private final String coinImageBaseUrl;

  public CoinDataInitializer(
      CoinRepository coinRepository,
      @Qualifier("coinImageBaseUrl") String coinImageBaseUrl
  ) {
    this.coinRepository = coinRepository;
    this.coinImageBaseUrl = coinImageBaseUrl;
  }

  @Transactional()
  public void run() {
    this.registerCoin(KLAY_ADDRESS, "KLAY", "클레이튼", 18, "KLAY", "coin_logo_klay");
    this.registerCoin(KSP_ADDRESS, "KSP", "클레이스왑 프로토콜", 18, "KSP", "coin_logo_ksp");
    this.registerCoin(KETH_ADDRESS, "KETH", "이더리움", 18, "ETH", "coin_logo_keth");
    this.registerCoin(KWBTC_ADDRESS, "KWBTC", "랩드비트코인", 8, "BTC", "coin_logo_kwbtc");
    this.registerCoin(KUSDT_ADDRESS, "KUSDT", "USDT", 6, "USDT", "coin_logo_empty");
    this.registerCoin(KDAI_ADDRESS, "KDAI", "DAI", 18, "DAI", "coin_logo_empty");
    this.registerCoin(SKLAY_ADDRESS, "SKLAY", "에스클레이", 18, "SKLAY", "coin_logo_empty");
    this.registerCoin(KORC_ADDRESS, "KORC", "오르빗체인", 18, "ORC", "coin_logo_empty");
    this.registerCoin(KXRP_ADDRESS, "KXRP", "리플", 6, "XRP", "coin_logo_empty");
    this.registerCoin(sKAI_ADDRESS, "sKAI", "카이쉐어토큰", 18, "sKAI", "coin_logo_empty");
    this.registerCoin(ABL_ADDRESS, "ABL", "에어블록", 18, "ABL", "coin_logo_empty");
    this.registerCoin(KBNB_ADDRESS, "KBNB", "바이낸스코인", 18, "BNB", "coin_logo_empty");
    this.registerCoin(KBELT_ADDRESS, "KBELT", "벨트", 18, "BELT", "coin_logo_empty");
    this.registerCoin(FINIX_ADDRESS, "FINIX", "디피닉스", 18, "FINIX", "coin_logo_empty");
    this.registerCoin(KFI_ADDRESS, "KFI", "클레이파이", 18, "KFI", "coin_logo_empty");
    this.registerCoin(AKLAY_ADDRESS, "AKLAY", "AKLAY", 18, "AKLAY", "coin_logo_empty");
    this.registerCoin(BKAI_ADDRESS, "BKAI", "카이본드토큰", 18, "BKAI", "coin_logo_empty");
    this.registerCoin(KAI_ADDRESS, "KAI", "카이토큰", 18, "KKAI", "coin_logo_empty");
    this.registerCoin(VKAI_ADDRESS, "VKAI", "카이토큰 Vote", 18, "VKAI", "coin_logo_empty");
    this.registerCoin(KOKOA_ADDRESS, "KOKOA", "코코아", 18, "KOKOA", "coin_logo_empty");
    this.registerCoin(KSD_ADDRESS, "KSD", "KSD", 18, "KSD", "coin_logo_empty");
    this.registerCoin(HOUSE_ADDRESS, "HOUSE", "HOUSE", 18, "HOUSE", "coin_logo_empty");
    this.registerCoin(WOOD_ADDRESS, "WOOD", "WOOD", 18, "WOOD", "coin_logo_empty");
    this.registerCoin(KUSDC_ADDRESS, "KUSDC", "KUSDC", 6, "USDC", "coin_logo_empty");
    this.registerCoin(WEMIX_ADDRESS, "WEMIX", "위믹스", 18, "WEMIX", "coin_logo_empty");
    this.registerCoin(SKAI_ADDRESS, "SKAI", "카이쉐어토큰", 18, "SKAI", "coin_logo_empty");
    this.registerCoin(KRONOS_ADDRESS, "KRNO", "크로노스", 9, "KRNO", "coin_logo_empty");
  }

  private void registerCoin(
      Address coinAddress,
      String symbol,
      String name,
      int decimal,
      String originalSymbol,
      String symbolImageName
  ) {
    this.coinRepository.findByAddress(coinAddress)
        .orElseGet(() -> {
          Coin coin = Coin.withoutPrice(
              coinAddress,
              symbol,
              name,
              decimal,
              originalSymbol,
              this.getCoinSymbolImageUrl(symbolImageName)
          );
          return this.coinRepository.save(coin);
        });
  }

  private String getCoinSymbolImageUrl(String imageName) {
    return this.coinImageBaseUrl + imageName + ".png";
  }
}
