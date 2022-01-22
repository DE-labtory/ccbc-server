package io.coin.ccbc.kokoa;

import static org.assertj.core.api.Assertions.assertThat;

import com.klaytn.caver.Caver;
import com.klaytn.caver.crypto.KlayCredentials;
import com.klaytn.caver.tx.gas.DefaultGasProvider;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Commodity;
import io.coin.ccbc.domain.DefiInformation;
import io.coin.ccbc.domain.DomainEntity;
import io.coin.ccbc.domain.InvestmentAsset;
import io.coin.ccbc.domain.Price;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = {
    Coin.class,
    CoinRepository.class
})
@EntityScan(basePackageClasses = {
    Coin.class,
    CoinRepository.class
})
@ContextConfiguration(classes = {CoinRepository.class, DomainEntity.class})
@TestPropertySource("classpath:application-test.yml")
@DisplayName("Kokoa-Finance")
public class KokoaFinanceTests {

  private final Coin kokoa = Coin.withPrice(
      KokoaFinance.KOKOA_ADDRESS,
      "KOKOA",
      "코코아",
      18,
      "KOKOA",
      "",
      Price.from(1700)
  );
  private final Coin klay = Coin.withPrice(
      KokoaFinance.KLAY_ADDRESS,
      "KLAY",
      "클레이",
      18,
      "KLAY",
      "",
      Price.from(182000)
  );
  private final Coin aklay = Coin.withPrice(
      KokoaFinance.AKLAY_ADDRESS,
      "AKLAY",
      "AKLAY",
      18,
      "AKLAY",
      "",
      Price.from(182000)
  );
  private final Coin ksd = Coin.withPrice(
      KokoaFinance.KSD_ADDRESS,
      "KSD",
      "ksd",
      18,
      "KSD",
      "",
      Price.from(1200)
  );
  private final Coin usdt = Coin.withPrice(
      KokoaFinance.USDT_ADDRESS,
      "kUSDT",
      "USDT",
      6,
      "kUSDT",
      "",
      Price.from(1200)
  );
  private final Coin dai = Coin.withPrice(
      Address.of("0x5c74070fdea071359b86082bd9f9b3deaafbe32b"),
      "kDAI",
      "kDai",
      6,
      "DAI",
      "",
      Price.from(1200)
  );
  private final Coin kwbtc = Coin.withPrice(
      Address.of("0x16d0e1fbd024c600ca0380a4c5d57ee7a2ecbf9c"),
      "kWBTC",
      "랩드비트코인",
      8,
      "BTC",
      "",
      Price.from(1200)
  );
  private final Coin kxrp = Coin.withPrice(
      Address.of("0x9eaefb09fe4aabfbe6b1ca316a3c36afc83a393f"),
      "KXRP",
      "리플",
      6,
      "XRP",
      "",
      Price.from(1200)
  );
  private final Coin ksp = Coin.withPrice(
      Address.of("0xc6a2ad8cc6e4a7e08fc37cc5954be07d499e7654"),
      "KSP",
      "클레이스왑 프로토콜",
      18,
      "KSP",
      "",
      Price.from(1200)
  );
  private final Coin kusdc = Coin.withPrice(
      Address.of("0x754288077d0ff82af7a5317c7cb8c444d421d103"),
      "KUSDC",
      "KUSDC",
      6,
      "USDC",
      "",
      Price.from(1200)
  );
  private final Coin keth = Coin.withPrice(
      Address.of("0x34d21b1e550d73cee41151c77f3c73359527a396"),
      "KETH",
      "이더리움",
      18,
      "ETH",
      "",
      Price.from(1200)
  );
  private KokoaFinance kokoaFinance;
  @Autowired
  private CoinRepository coinRepository;

  @BeforeEach
  void setUp() {
    this.coinRepository.save(this.dai);
    this.coinRepository.save(this.klay);
    this.coinRepository.save(this.aklay);
    this.coinRepository.save(this.kokoa);
    this.coinRepository.save(this.usdt);
    this.coinRepository.save(this.ksd);
    this.coinRepository.save(this.kwbtc);
    this.coinRepository.save(this.keth);
    this.coinRepository.save(this.kusdc);
    this.coinRepository.save(this.ksp);
    this.coinRepository.save(this.kxrp);
    this.kokoaFinance = new KokoaFinance(new HttpKoKoaProtocolClient(CCBCKokoaViewer.load(
        "0x202C535f0aa67d32Cf9779A1b2986785326c2486",
        Caver.build( "https://public-node-api.klaytnapi.com/v1/cypress"),
        KlayCredentials.create(
            "0x34dd269ddc4eef3671098e7dc2323eecab7d782eb6cc4e8d4ea2d5d8b595b81a" // 0 클레이 깡통계정
        ),
        8217,
        new DefaultGasProvider()
    )), coinRepository);
  }

  @Test
  @DisplayName("디파이 정보를 조회합니다.")
  public void getInformation() {
    DefiInformation defiInformation = kokoaFinance.getInformation();
    assertThat(defiInformation.getName()).isEqualTo(KokoaFinance.PROTOCOL_NAME);
    assertThat(defiInformation.getTvl()).isNotEqualTo(0);
    assertThat(defiInformation.getColor()).isEqualTo("B8AA9F");
    assertThat(defiInformation.getSymbolUrl()).isEqualTo(
        "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/kokoa.png");
  }

  @Nested
  @DisplayName("GetAllCommodities")
  class GetAllCommodities {

    @Test
    @DisplayName("상품목록을 조회합니다.")
    public void getAllCommodities() {
      List<Commodity> commodities = kokoaFinance.getAllCommodities();
      commodities.stream().forEach(commodity -> {
        System.out.println(commodity.getName());
        System.out.println(commodity.getApr());
      });
    }
  }

  @Nested
  @DisplayName("GetAllInvestmentAssets")
  class GetAllInvestmentAssets {

    @Test
    @DisplayName("투자 상품을 조회합니다.")
    public void getAllCommodities() {
      List<InvestmentAsset> investmentAssets = kokoaFinance.getAllInvestmentAssets(Address.of("0xCA5e65f9E394c47FF3585e21612d32ABc3436501"));
      investmentAssets.stream().forEach(investmentAsset -> {
        System.out.println(investmentAsset.getCommodity().getAddress());
//        System.out.println(commodity.getApr());
      });
    }
  }
}
