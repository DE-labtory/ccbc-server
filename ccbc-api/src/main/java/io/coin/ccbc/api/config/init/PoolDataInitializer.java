package io.coin.ccbc.api.config.init;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.DexPair;
import io.coin.ccbc.domain.DexPair.Type;
import io.coin.ccbc.domain.DexPairRepository;
import io.coin.ccbc.klayswap.KlayswapPool;
import io.coin.ccbc.klayswap.KlayswapPoolRepository;
import java.util.NoSuchElementException;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class PoolDataInitializer {

  private final KlayswapPoolRepository klayswapPoolRepository;
  private final DexPairRepository dexPairRepository;
  private final CoinRepository coinRepository;


  public static final Address KLAY_KETH_POOL_ADDRESS =
      Address.of("0x27F80731dDdb90C51cD934E9BD54bfF2D4E99e8a");
  public static final Address KETH_KUSDT_POOL_ADDRESS =
      Address.of("0x029e2A1B2bb91B66bd25027E1C211E5628dbcb93");
  public static final Address KETH_KWBTC_POOL_ADDRESS =
      Address.of("0x2A6A4b0c96cA98eB691a5ddceE3c7b7788c1a8E3");
  public static final Address KUSDT_KDAI_POOL_ADDRESS =
      Address.of("0xc320066b25B731A11767834839Fe57f9b2186f84");
  public static final Address KLAY_SKLAY_POOL_ADDRESS =
      Address.of("0x073FDe66B725D0eF5b54059ACa22bBFC63a929ce");
  public static final Address KLAY_KSP_POOL_ADDRESS =
      Address.of("0x34cF46c21539e03dEb26E4FA406618650766f3b9");
  public static final Address KLAY_KORC_POOL_ADDRESS =
      Address.of("0xe9ddb7A6994bD9cDF97CF11774A72894597D878B");
  public static final Address KSP_KORC_POOL_ADDRESS =
      Address.of("0x6dc6bd65638B18057F7E6a2e8f136F3E77CC2038");
  public static final Address KLAY_KXRP_POOL_ADDRESS =
      Address.of("0x86E614ef51B305C36a0608bAa57fcaaa45844D87");
  public static final Address KLAY_KUSDT_POOL_ADDRESS =
      Address.of("0xD83f1B074D81869EFf2C46C530D7308FFEC18036");
  public static final Address KLAY_KDAI_POOL_ADDRESS =
      Address.of("0xa3987cf6C14F1992e8b4a9E23192Eb79dC2969b8");
  public static final Address KLAY_sKAI_POOL_ADDRESS =
      Address.of("0x0734f80fbC2051E98e6C7cB37E08E067A9630c06");
  public static final Address KSP_sKAI_POOL_ADDRESS =
      Address.of("0x6456acb56f9eeedb976d5d72b60fb31720155b75");
  public static final Address KWBTC_KUSDT_POOL_ADDRESS =
      Address.of("0x9103Beb39283dA9C49B020D6546FD7C39f9bbc5b");
  public static final Address KORC_KUSDT_POOL_ADDRESS =
      Address.of("0x94F390a8438b5De00B868d3aE47863Db90fB92c3");
  public static final Address KORC_KDAI_POOL_ADDRESS =
      Address.of("0x587a01F81E5c078CD7C03F09f45705530fFB7B94");
  public static final Address KSP_KXRP_POOL_ADDRESS =
      Address.of("0xA06B9a38a7b4B91CB5d9B24538296bfB3B97fBf3");
  public static final Address KSP_KWBTC_POOL_ADDRESS =
      Address.of("0x85Fae50259EbB9a86F49BDBfb8dBaEC84a7ED5fe");
  public static final Address KSP_KETH_POOL_ADDRESS =
      Address.of("0xa8F8f1153523eAeDce48CEc2Ddbe1Bcd483d0CD8");
  public static final Address KORC_KXRP_POOL_ADDRESS =
      Address.of("0x805CB5eB7063f132cEAf56b2c7182c897a024a83");
  public static final Address KLAY_ABL_POOL_ADDRESS =
      Address.of("0x9609861EEC1DC15756fD0F5429FB96E475790920");
  public static final Address ABL_KORC_POOL_ADDRESS =
      Address.of("0xa2867C345f9b7250Fe6BE6CCCB6360dff9F6E38c");
  public static final Address KSP_KDAI_POOL_ADDRESS =
      Address.of("0x64E58F35e9D4e2aB6380908905177cE150aa8608");
  public static final Address KSP_KUSDT_POOL_ADDRESS =
      Address.of("0xE75a6A3a800A2C5123e67e3bde911Ba761FE0705");
  public static final Address KXRP_KDAI_POOL_ADDRESS =
      Address.of("0x4B50d0e4F29bF5B39a6234B11753fDB3b28E76Fc");
  public static final Address KSP_KBNB_POOL_ADDRESS =
      Address.of("0x7328B85eFF28C3068F69FE662632d37D48ba227f");
  public static final Address KLAY_KBNB_POOL_ADDRESS =
      Address.of("0xE20B9aeAcAC615Da0fdBEB05d4F75E16FA1F6B95");
  public static final Address KLAY_KBELT_POOL_ADDRESS =
      Address.of("0x157c39202fAE6233FEc3f8B3bCb2158200d0A863");
  public static final Address KBELT_KORC_POOL_ADDRESS =
      Address.of("0x0f14648eD03A4172a0D186dA51b66e7e9Af6af66");
  public static final Address KETH_KBNB_POOL_ADDRESS =
      Address.of("0x8119f0CeC72a26fE23CA1aB076137Ea5D8a19d54");
  public static final Address KORC_sKAI_POOL_ADDRESS =
      Address.of("0x8CA4010C2a3FF730270559C96e0c94D0CAc04491");
  public static final Address KETH_KORC_POOL_ADDRESS =
      Address.of("0x9E1cD9645EB2753d4D4A94786bB76cC99adb689b");
  public static final Address KLAY_FINIX_POOL_ADDRESS =
      Address.of("0x298ED5f16523eEe6E32FA9bcfD675B58878d823B");
  public static final Address KLAY_KFI_POOL_ADDRESS =
      Address.of("0xd74d4b4d2fb186bb7f31e4000c59ade70bbd8a23");
  public static final Address KLAY_AKLAY_POOL_ADDRESS =
      Address.of("0xe74c8d8137541c0ee2c471cdaf4dcf03c383cd22");
  public static final Address KLAY_KOKOA_POOL_ADDRESS =
      Address.of("0x4bfcc93fb85c969a590a2e7d7a4ad72f0668aff2");
  public static final Address KLAY_KSD_POOL_ADDRESS =
      Address.of("0xd52acc40924c906d3eeab239d6f6c36b612011af");
  public static final Address KSD_KDAI_POOL_ADDRESS =
      Address.of("0x797ff7657f998cfa64288d8c0626aa7cc8190c2d");
  public static final Address KSD_KUSDT_POOL_ADDRESS =
      Address.of("0xc9ad2f9c45c9cc3128c898fe55c14a146e7d1d88");
  public static final Address HOUSE_WOOD_POOL_ADDRESS =
      Address.of("0xf05200168ff2e1b0848cf4981184017f94710fa6");
  public static final Address AKLAY_HOUSE_POOL_ADDRESS =
      Address.of("0xafedb9fd20d0007a13dcd685a89825cd2866f0fc");
  public static final Address KLAY_HOUSE_POOL_ADDRESS =
      Address.of("0x8ad37f3f3fb663a67c7140947f80894a973b789b");
  public static final Address KLAY_KUSDC_POOL_ADDRESS =
      Address.of("0x3bce8d81ac54010bb7ea6e5960f2ded6fc6a7ac5");
  public static final Address KUSDT_KUSDC_POOL_ADDRESS =
      Address.of("0x2e9269b718cc816de6a9e3c27e5bdb0f6a01b0ac");
  public static final Address KLAY_VKAI_POOL_ADDRESS =
      Address.of("0xded9d5e668ee98ac1499446012ea42e7306b8190");
  public static final Address KLAY_WEMIX_POOL_ADDRESS =
      Address.of("0x917eed7ae9e7d3b0d875dd393af93fff3fc301f8");
  public static final Address KETH_KXRP_POOL_ADDRESS =
      Address.of("0x85ef87815bd7be28bee038ff201db78c7e0ed2b9");
  public static final Address KUSDT_KAI_POOL_ADDRESS =
      Address.of("0x5787492d753d5f59365e2f98e2f18c3ae3bad6e7");
  public static final Address KDAI_KAI_POOL_ADDRESS =
      Address.of("0x92e51bf6ec87623beb774f7356629b61005b1586");
  public static final Address SKAI_VKAI_POOL_ADDRESS =
      Address.of("0xa880ce416eee50f3d13bf5af08b8d31f2a2486c0");
  public static final Address KLAY_SKAI_POOL_ADDRESS =
      Address.of("0x0734f80fbc2051e98e6c7cb37e08e067a9630c06");
  public static final Address KSP_SKAI_POOL_ADDRESS =
      Address.of("0x6456acb56f9eeedb976d5d72b60fb31720155b75");

  public PoolDataInitializer(
      KlayswapPoolRepository klayswapPoolRepository,
      DexPairRepository dexPairRepository,
      CoinRepository coinRepository
  ) {
    this.klayswapPoolRepository = klayswapPoolRepository;
    this.coinRepository = coinRepository;
    this.dexPairRepository = dexPairRepository;
  }

  @Transactional
  public void run() {
//    Dex klaySwapDex = this.dexRepository.findByAddress(KLAYSWAP_DEX_ADDRESS)
//        .orElseGet(() -> {
//          Coin ksp = this.coinRepository.findByAddress(CoinDataInitializer.KSP_ADDRESS)
//              .orElseThrow(() -> new IllegalStateException("ksp is required."));
//
//          return this.dexRepository.save(Dex.of(
//              "클레이스왑",
//              KLAYSWAP_DEX_ADDRESS,
//              ksp,
//              0.3
//          ));
//        });

    this.registerPool(KLAY_KETH_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS);

    this.registerPool(KETH_KUSDT_POOL_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS);

    this.registerPool(KETH_KWBTC_POOL_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS,
        CoinDataInitializer.KWBTC_ADDRESS);

    this.registerPool(KUSDT_KDAI_POOL_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS);

    this.registerPool(KLAY_SKLAY_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.SKLAY_ADDRESS);

    this.registerPool(KLAY_KSP_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS);

    this.registerPool(KLAY_KORC_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS);

    this.registerPool(KSP_KORC_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS);

    this.registerPool(KLAY_KXRP_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KXRP_ADDRESS);

    this.registerPool(KLAY_KUSDT_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS);

    this.registerPool(KLAY_KDAI_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS);

    this.registerPool(KLAY_sKAI_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.sKAI_ADDRESS);

    this.registerPool(KSP_sKAI_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.sKAI_ADDRESS);

    this.registerPool(KWBTC_KUSDT_POOL_ADDRESS,
        CoinDataInitializer.KWBTC_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS);

    this.registerPool(KORC_KUSDT_POOL_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS);

    this.registerPool(KORC_KDAI_POOL_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS);

    this.registerPool(KSP_KXRP_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KXRP_ADDRESS);

    this.registerPool(KSP_KWBTC_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KWBTC_ADDRESS);

    this.registerPool(KSP_KETH_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS);

    this.registerPool(KORC_KXRP_POOL_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS,
        CoinDataInitializer.KXRP_ADDRESS);

    this.registerPool(KLAY_ABL_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.ABL_ADDRESS);

    this.registerPool(ABL_KORC_POOL_ADDRESS,
        CoinDataInitializer.ABL_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS);

    this.registerPool(KSP_KDAI_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS);

    this.registerPool(KSP_KUSDT_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS);

    this.registerPool(KXRP_KDAI_POOL_ADDRESS,
        CoinDataInitializer.KXRP_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS);

    this.registerPool(KSP_KBNB_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.KBNB_ADDRESS);

    this.registerPool(KLAY_KBNB_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KBNB_ADDRESS);

    this.registerPool(KLAY_KBELT_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KBELT_ADDRESS);

    this.registerPool(KBELT_KORC_POOL_ADDRESS,
        CoinDataInitializer.KBELT_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS);

    this.registerPool(KETH_KBNB_POOL_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS,
        CoinDataInitializer.KBNB_ADDRESS);

    this.registerPool(KORC_sKAI_POOL_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS,
        CoinDataInitializer.sKAI_ADDRESS);

    this.registerPool(KETH_KORC_POOL_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS,
        CoinDataInitializer.KORC_ADDRESS);
    this.registerPool(KLAY_FINIX_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.FINIX_ADDRESS);

    this.registerPool(KLAY_KFI_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KFI_ADDRESS);

    this.registerPool(KLAY_AKLAY_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.AKLAY_ADDRESS);

    this.registerPool(KLAY_KOKOA_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KOKOA_ADDRESS);

    this.registerPool(KLAY_KSD_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KSD_ADDRESS);

    this.registerPool(KSD_KDAI_POOL_ADDRESS,
        CoinDataInitializer.KSD_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS);

    this.registerPool(KSD_KUSDT_POOL_ADDRESS,
        CoinDataInitializer.KSD_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS);

    this.registerPool(HOUSE_WOOD_POOL_ADDRESS,
        CoinDataInitializer.HOUSE_ADDRESS,
        CoinDataInitializer.WOOD_ADDRESS);

    this.registerPool(AKLAY_HOUSE_POOL_ADDRESS,
        CoinDataInitializer.AKLAY_ADDRESS,
        CoinDataInitializer.HOUSE_ADDRESS);

    this.registerPool(KLAY_HOUSE_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.HOUSE_ADDRESS);

    this.registerPool(KLAY_KUSDC_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.KUSDC_ADDRESS);

    this.registerPool(KUSDT_KUSDC_POOL_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS,
        CoinDataInitializer.KUSDC_ADDRESS);

    this.registerPool(KLAY_VKAI_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.VKAI_ADDRESS);

    this.registerPool(KLAY_WEMIX_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.WEMIX_ADDRESS);

    this.registerPool(KETH_KXRP_POOL_ADDRESS,
        CoinDataInitializer.KETH_ADDRESS,
        CoinDataInitializer.KXRP_ADDRESS);

    this.registerPool(KUSDT_KAI_POOL_ADDRESS,
        CoinDataInitializer.KUSDT_ADDRESS,
        CoinDataInitializer.KAI_ADDRESS);

    this.registerPool(KDAI_KAI_POOL_ADDRESS,
        CoinDataInitializer.KDAI_ADDRESS,
        CoinDataInitializer.KAI_ADDRESS);

    this.registerPool(SKAI_VKAI_POOL_ADDRESS,
        CoinDataInitializer.SKAI_ADDRESS,
        CoinDataInitializer.VKAI_ADDRESS);

    this.registerPool(KLAY_SKAI_POOL_ADDRESS,
        CoinDataInitializer.KLAY_ADDRESS,
        CoinDataInitializer.SKAI_ADDRESS);

    this.registerPool(KSP_SKAI_POOL_ADDRESS,
        CoinDataInitializer.KSP_ADDRESS,
        CoinDataInitializer.SKAI_ADDRESS);
  }

  private void registerPool(
      Address poolAddress,
      Address coin0Address,
      Address coin1Address
  ) {
    KlayswapPool klayswapPool = this.klayswapPoolRepository.findByAddress(poolAddress)
        .orElseGet(() -> {
          Coin coin0 = this.coinRepository.findByAddress(coin0Address)
              .orElseThrow(() -> {
                throw new NoSuchElementException();
              });
          Coin coin1 = this.coinRepository.findByAddress(coin1Address)
              .orElseThrow(() -> {
                throw new NoSuchElementException();
              });
          return this.klayswapPoolRepository.save(KlayswapPool.of(
              poolAddress,
              18,
              coin0,
              coin1
          ));
        });
    this.dexPairRepository.findByAddress(poolAddress)
        .orElseGet(() -> {
          Coin coin0 = this.coinRepository.findByAddress(coin0Address)
              .orElseThrow(() -> {
                throw new NoSuchElementException();
              });
          Coin coin1 = this.coinRepository.findByAddress(coin1Address)
              .orElseThrow(() -> {
                throw new NoSuchElementException();
              });
          return this.dexPairRepository.save(
              new DexPair(poolAddress, 18, coin0, coin1, Type.KLAYSWAP));
        });

  }
}
