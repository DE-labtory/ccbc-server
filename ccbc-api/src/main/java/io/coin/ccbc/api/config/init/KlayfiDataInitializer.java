package io.coin.ccbc.api.config.init;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.klayfi.domain.KlayfiCommodity;
import io.coin.ccbc.klayfi.domain.KlayfiCommodity.Type;
import io.coin.ccbc.klayfi.domain.KlayfiCommodityRepository;
import io.coin.ccbc.klayswap.KlayswapPool;
import io.coin.ccbc.klayswap.KlayswapPoolRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class KlayfiDataInitializer {

  public static final Address STAKING_POOL = Address
      .of("0xE8bf8325Fd25983Ba24d3305eC50185c61Cc35E5");
  public static final Address KLAY_KFI_VAULT = Address
      .of("0x28Aa8Ed20B56e68f6C4cF049f987ED6b411B0aC5");
  public static final Address KLAY_AKLAY_VAULT = Address
      .of("0x41951d1EE1015d06b4ecD3222bdEE748E7606FDF");
  public static final Address KUSDT_KDAI_VAULT = Address
      .of("0x65575bd5EB7556731Ef058A3F5606811ABC6862A");
  public static final Address KLAY_KOKOA_VAULT = Address
      .of("0x78ed813e1a985836a295dd67579b864b3e9c5685");
  public static final Address KLAY_KSD_VAULT = Address
      .of("0xba3a04d3d4eb2ca5033aebbca98779f83140ad16");
  public static final Address KSD_KDAI_VAULT = Address
      .of("0x134b282feed0e245a560a7b7d9267e1b684be325");
  public static final Address KSD_KUSDT_VAULT = Address
      .of("0x3f397048ea3315bdcaa2c6b83a2f4350f7723061");
  public static final Address HOUSE_WOOD_VAULT = Address
      .of("0x496845710b6507a630cd953bb50b50ef0038062b");
  public static final Address AKLAY_HOUSE_VAULT = Address
      .of("0x6a6Ef26cc914949D4DBd48edC7D126B0243D4A33");
  public static final Address KLAY_HOUSE_VAULT = Address
      .of("0x741Fa0Fa545085e81486B08E101083dC05564c8D");
  public static final Address KLAY_KUSDC_VAULT = Address
      .of("0xa9ee28b76c5e6d0b01ee208b0e1201122bd0546a");
  public static final Address KUSDT_KUSDC_VAULT = Address
      .of("0xDfa0aE9BF0940164147d6198B8497B4c2B9BcDA6");
  public static final Address KLAY_VKAI_VAULT = Address
      .of("0xDd0bd9BF15ABC647FB1460a4B9F0A0FC05d67Ca2");
  public static final Address KLAY_WEMIX_VAULT = Address
      .of("0xfa87661c69fdf27faA75F19D8348c6c38CfEc7E2");
  public static final Address KLAY_SKLAY_VAULT = Address
      .of("0x121C8CBC42406e6e7e7B2a3e6d42eD6ca880427B");
  public static final Address KLAY_KSP_VAULT = Address
      .of("0xc867092a63153354C6A4afa2c4A5e985f6f7A353");
  public static final Address KETH_KUSDT_VAULT = Address
      .of("0x4976001b2210507B5389e409C9A475Ea1302Ab80");
  public static final Address KLAY_KETH_VAULT = Address
      .of("0x88bAC5B578763dC1f9980535165294f7Beef3050");
  public static final Address KLAY_KORC_VAULT = Address
      .of("0xE7da1bB047076f1E2C7Bc2981685149643F2b9E1");
  public static final Address KSP_KORC_VAULT = Address
      .of("0xC874264FAd75065C1E95d280f814b3EcAdd1f7E8");
  public static final Address KETH_KWBTC_VAULT = Address
      .of("0x211c7005F40F8769c8BeA3c74c4b0621544380Ef");
  public static final Address KLAY_KUSDT_VAULT = Address
      .of("0x236fDB697E360932ec3047497dCeab87Dd04985F");
  public static final Address KLAY_KXRP_VAULT = Address
      .of("0x3B284d598F06f41be54764A2d41F387f0e5878F0");
  public static final Address KLAY_KDAI_VAULT = Address
      .of("0x13FfC592DcccaF011AcC620120D7F82C92f93291");
  public static final Address KSP_KETH_VAULT = Address
      .of("0x4Ab83F290f8f579e28d39333923399A59c16C474");
  public static final Address KORC_KUSDT_VAULT = Address
      .of("0x85F8363DB035C469342A19627269839907F195A8");
  public static final Address KORC_KXRP_VAULT = Address
      .of("0x07B390e5BD07C58f46B81B335Ae2eaD12Ce8d971");
  public static final Address KORC_KDAI_VAULT = Address
      .of("0x96848F753B55942DFAF8fE268c732f84a477b7e1");
  public static final Address KLAY_KBNB_VAULT = Address
      .of("0xa41e658D1c93ADbCd473CbC8aC983830323dab9f");
  public static final Address KSP_KUSDT_VAULT = Address
      .of("0x775Bcc7aDb8B38F3fF0e4aeDF923b24A63C4b92a");
  public static final Address KXRP_KDAI_VAULT = Address
      .of("0xcd0a779F48b34482B1D607DA57055bbF7BeCc465");
  public static final Address KSP_KXRP_VAULT = Address
      .of("0x6aCDC61320C71b941e6711c113c99b29D2B9a973");
  public static final Address KETH_KXRP_VAULT = Address
      .of("0xF57315b77f55DB36d303f4bFD77abCC4a25C41C1");
  public static final Address KSP_SKAI_VAULT = Address
      .of("0xc05758d5dEbaD7Ca8Dc4fA0E7D0C09eCb44cf3f2");
  public static final Address KLAY_SKAI_VAULT = Address
      .of("0x475b99930Fafd621A8d1048B3dF64F7978E9aE02");
  public static final Address KUSDT_KAI_VAULT = Address
      .of("0x54a0CC7fF961fa95da2323759d705849f8f8a659");
  public static final Address KDAI_KAI_VAULT = Address
      .of("0xde6975e0ba1b6473cec31af402dabed96fc5a329");
  public static final Address SKAI_VKAI_VAULT = Address
      .of("0x1BA64368cBd43Be7283B4d16Ad4d8226Cd2333Fb");
  public static final Address KLAY_KBELT_VAULT = Address
      .of("0x2f565cfA70360702F93aB0abb106Bd86A9757E79");
  public static final Address KBELT_KORC_VAULT = Address
      .of("0x9a342E7e84b11414B636C039Faad88DF56436d74");

  private final KlayfiCommodityRepository klayfiCommodityRepository;
  private final KlayswapPoolRepository poolRepository;
  private final CoinRepository coinRepository;

  public KlayfiDataInitializer(
      KlayfiCommodityRepository klayfiCommodityRepository,
      KlayswapPoolRepository poolRepository,
      CoinRepository coinRepository
  ) {
    this.klayfiCommodityRepository = klayfiCommodityRepository;
    this.poolRepository = poolRepository;
    this.coinRepository = coinRepository;
  }

  @Transactional
  public void run() {
    Coin kfi = this.coinRepository.findBySymbolOrElseThrow("KFI");
    this.registerStakingPool(STAKING_POOL, kfi);
    this.registerVault(KLAY_KFI_VAULT, PoolDataInitializer.KLAY_KFI_POOL_ADDRESS, kfi, Type.CORE_VAULT);

    // groth
    this.registerVault(KLAY_AKLAY_VAULT, PoolDataInitializer.KLAY_AKLAY_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(KLAY_KOKOA_VAULT, PoolDataInitializer.KLAY_KOKOA_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(KLAY_KSD_VAULT, PoolDataInitializer.KLAY_KSD_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(KSD_KDAI_VAULT, PoolDataInitializer.KSD_KDAI_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(KSD_KUSDT_VAULT, PoolDataInitializer.KSD_KUSDT_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(HOUSE_WOOD_VAULT, PoolDataInitializer.HOUSE_WOOD_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(AKLAY_HOUSE_VAULT, PoolDataInitializer.AKLAY_HOUSE_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);
    this.registerVault(KLAY_HOUSE_VAULT, PoolDataInitializer.KLAY_HOUSE_POOL_ADDRESS, kfi, Type.GROWTH_VAULT);

    // prime
    this.registerVault(KLAY_KUSDC_VAULT, PoolDataInitializer.KLAY_KUSDC_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KUSDT_KUSDC_VAULT, PoolDataInitializer.KUSDT_KUSDC_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_VKAI_VAULT, PoolDataInitializer.KLAY_VKAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_WEMIX_VAULT, PoolDataInitializer.KLAY_WEMIX_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_SKLAY_VAULT, PoolDataInitializer.KLAY_SKLAY_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KUSDT_KDAI_VAULT, PoolDataInitializer.KUSDT_KDAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KSP_VAULT, PoolDataInitializer.KLAY_KSP_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KETH_KUSDT_VAULT, PoolDataInitializer.KETH_KUSDT_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KETH_VAULT, PoolDataInitializer.KLAY_KETH_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KORC_VAULT, PoolDataInitializer.KLAY_KORC_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KSP_KORC_VAULT, PoolDataInitializer.KSP_KORC_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KETH_KWBTC_VAULT, PoolDataInitializer.KETH_KWBTC_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KUSDT_VAULT, PoolDataInitializer.KLAY_KUSDT_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KXRP_VAULT, PoolDataInitializer.KLAY_KXRP_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KDAI_VAULT, PoolDataInitializer.KLAY_KDAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KSP_KETH_VAULT, PoolDataInitializer.KSP_KETH_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KORC_KUSDT_VAULT, PoolDataInitializer.KORC_KUSDT_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KORC_KXRP_VAULT, PoolDataInitializer.KORC_KXRP_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KORC_KDAI_VAULT, PoolDataInitializer.KORC_KDAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KBNB_VAULT, PoolDataInitializer.KLAY_KBNB_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KSP_KUSDT_VAULT, PoolDataInitializer.KSP_KUSDT_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KXRP_KDAI_VAULT, PoolDataInitializer.KXRP_KDAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KSP_KXRP_VAULT, PoolDataInitializer.KSP_KXRP_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KETH_KXRP_VAULT, PoolDataInitializer.KETH_KXRP_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KSP_SKAI_VAULT, PoolDataInitializer.KSP_SKAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_SKAI_VAULT, PoolDataInitializer.KLAY_SKAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KUSDT_KDAI_VAULT, PoolDataInitializer.KUSDT_KDAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KUSDT_KAI_VAULT, PoolDataInitializer.KUSDT_KAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KDAI_KAI_VAULT, PoolDataInitializer.KDAI_KAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(SKAI_VKAI_VAULT, PoolDataInitializer.SKAI_VKAI_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KLAY_KBELT_VAULT, PoolDataInitializer.KLAY_KBELT_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
    this.registerVault(KBELT_KORC_VAULT, PoolDataInitializer.KBELT_KORC_POOL_ADDRESS, kfi, Type.PRIME_VAULT);
  }

  public void registerStakingPool(
      Address stakingPoolAddress,
      Coin kfi
  ) {
    this.klayfiCommodityRepository.findByAddress(stakingPoolAddress)
        .orElseGet(() -> this.klayfiCommodityRepository.save(
            new KlayfiCommodity(
                null,
                kfi,
                stakingPoolAddress,
                18,
                Type.CORE_POOL
            ))
        );
  }

  public void registerVault(
      Address vaultAddress,
      Address poolAddress,
      Coin kfi,
      KlayfiCommodity.Type type
  ) {
    KlayswapPool pool = this.poolRepository.findByAddressOrElseThrow(poolAddress);
    this.klayfiCommodityRepository.findByAddress(vaultAddress)
        .orElseGet(() -> this.klayfiCommodityRepository.save(
            new KlayfiCommodity(
                pool,
                kfi,
                vaultAddress,
                18,
                type
            )
        ));
  }
}
