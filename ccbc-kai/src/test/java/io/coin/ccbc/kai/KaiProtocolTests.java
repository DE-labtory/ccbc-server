package io.coin.ccbc.kai;

import static org.assertj.core.api.Assertions.assertThat;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Asset;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Commodity;
import io.coin.ccbc.domain.DomainEntity;
import io.coin.ccbc.domain.InvestmentAsset;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.Value;
import io.coin.ccbc.kai.infra.HttpKaiProtocolClient;
import io.coin.ccbc.kai.infra.selenium.SeleniumKaiProtocolCrawler;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
@DisplayName("KaiProtocol")
public class KaiProtocolTests {

  private static SeleniumKaiProtocolCrawler seleniumKaiProtocolCrawler;
  private final Coin vkai = Coin.withPrice(
      KaiProtocol.VKAI_ADDRESS,
      "vkai",
      "VKAI",
      18,
      "",
      "",
      Price.from(1700)
  );
  private final Coin skai = Coin.withPrice(
      KaiProtocol.SKAI_ADDRESS,
      "skai",
      "SKAI",
      18,
      "",
      "",
      Price.from(182000)
  );
  private final Coin kai = Coin.withPrice(
      KaiProtocol.KAI_ADDRESS,
      "kai",
      "KAI",
      18,
      "",
      "",
      Price.from(1200)
  );
  private final Coin usdt = Coin.withPrice(
      KaiProtocol.USDT_ADDRESS,
      "usdt",
      "USDT",
      6,
      "",
      "",
      Price.from(1200)
  );
  private KaiProtocol kaiProtocol;
  @Autowired
  private CoinRepository coinRepository;

  @BeforeAll
  static void beforeAll() throws IOException, InterruptedException {
    seleniumKaiProtocolCrawler = new SeleniumKaiProtocolCrawler(10000);
  }

  @AfterAll
  static void tearDown() {
    seleniumKaiProtocolCrawler.close();
  }

  @BeforeEach
  void setUp() {
    this.coinRepository.save(this.skai);
    this.coinRepository.save(this.vkai);
    this.coinRepository.save(this.kai);
    this.coinRepository.save(this.usdt);
    this.kaiProtocol = new KaiProtocol(coinRepository, new HttpKaiProtocolClient(
        "https://public-node-api.klaytnapi.com/v1/cypress",
        seleniumKaiProtocolCrawler
    ));
  }

  @Nested
  @DisplayName("GetAllCommodities")
  class GetAllCommodities {

    @Test
    @DisplayName("상품목록을 조회합니다.")
    public void getAllCommodities() {
      List<Commodity> commodities = kaiProtocol.getAllCommodities();
      assertThat(commodities.size()).isEqualTo(1);
      assertThat(commodities.get(0).getName()).isEqualTo("skai staking");
      assertThat(commodities.get(0).getAddress()).isEqualTo(KaiProtocol.BOARDROOM_ADDRESS);
      assertThat(commodities.get(0).getApr()).isNotEqualTo(0);
      assertThat(commodities.get(0).getTvl()).isNotEqualTo(0);
      assertThat(commodities.get(0).getRelatedCoin())
          .usingElementComparatorIgnoringFields(
              "createdAt", "updatedAt")
          .containsExactlyInAnyOrder(
              skai
          );
    }

    @Test
    @DisplayName("투자한 상품자산을 조회합니다.")
    public void getAllInvestmentAssets() {
      List<InvestmentAsset> investmentAssets = kaiProtocol.getAllInvestmentAssets(
          Address.of("0xCA5e65f9E394c47FF3585e21612d32ABc3436501"));
      assertThat(investmentAssets.size()).isEqualTo(1);

      Commodity commodity = investmentAssets.get(0).getCommodity();
      assertThat(commodity.getName()).isEqualTo("skai staking");
      assertThat(commodity.getAddress()).isEqualTo(KaiProtocol.BOARDROOM_ADDRESS);
      assertThat(commodity.getApr()).isNotEqualTo(0);
      assertThat(commodity.getTvl()).isNotEqualTo(0);
      assertThat(commodity.getRelatedCoin())
          .usingElementComparatorIgnoringFields("createdAt", "updatedAt")
          .containsExactlyInAnyOrder(
              skai
          );

      assertThat(investmentAssets.get(0).getReceivedInterestValue()).isEqualTo(Value.zero());
      assertThat(investmentAssets.get(0).getReceivableInterestValue()).isNotEqualTo(Value.zero());
      assertThat(investmentAssets.get(0).getAssets()).containsExactlyInAnyOrder(new Asset(
          skai,
          Amount.of(new BigInteger("31741000000000000")),
          Value.of(
              Amount.of(new BigInteger("31741000000000000")),
              skai.getPrice(),
              skai.getDecimals()
          )
      ));
    }
  }
}
