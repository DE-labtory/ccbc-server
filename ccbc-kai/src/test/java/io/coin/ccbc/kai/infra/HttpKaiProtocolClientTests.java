package io.coin.ccbc.kai.infra;

import static org.assertj.core.api.Assertions.assertThat;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.kai.Reward;
import io.coin.ccbc.kai.infra.selenium.SeleniumKaiProtocolCrawler;
import java.io.IOException;
import java.math.BigInteger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpKaiProtocolClientTests {

  private static SeleniumKaiProtocolCrawler seleniumKaiProtocolCrawler;
  private HttpKaiProtocolClient httpKaiProtocolClient;

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
    this.httpKaiProtocolClient = new HttpKaiProtocolClient(
        "https://public-node-api.klaytnapi.com/v1/cypress",
        seleniumKaiProtocolCrawler
    );
  }

  @Test
  @DisplayName("[getStakedBalance] 예치된 잔고를 조회한다")
  void getStakedBalance() {
    Amount amount = this.httpKaiProtocolClient.getStakedBalance(
        Address.of("0xCA5e65f9E394c47FF3585e21612d32ABc3436501"));
    assertThat(amount).isEqualTo(Amount.of(new BigInteger("31741000000000000")));
  }

  @Test
  @DisplayName("[getStakedBalance] 예치된 잔고를 조회한다")
  void getStakedBalance_WhenNoDeposit() {
    Amount amount = this.httpKaiProtocolClient.getStakedBalance(
        Address.of("0x2953FdaDF72c0d89343bD48e81241d2698aD1a1A"));
    assertThat(amount).isEqualTo(Amount.zero());
  }

  @Test
  @DisplayName("[getReward] Reward를 조회한다")
  void getReward_WhenThereIsAReward() {
    Reward reward = this.httpKaiProtocolClient.getReward(
        Address.of("0xCA5e65f9E394c47FF3585e21612d32ABc3436501"));

    // reward값이 계속 변하기 때문에 0이 아닌것으로 테스트를 대체함
    assertThat(reward.getKaiAmount()).isNotEqualTo(Amount.zero());
    assertThat(reward.getVkaiAmount()).isNotEqualTo(Amount.zero());
  }

  @Test
  @DisplayName("[getReward] 수령 가능한 보상이 존재하는 않는 경우 Reward는 0으로 조회된다")
  void getReward_WhenThereIsNoReward() {
    Reward reward = this.httpKaiProtocolClient.getReward(
        Address.of("0x2953FdaDF72c0d89343bD48e81241d2698aD1a1A"));

    assertThat(reward.getKaiAmount()).isEqualTo(Amount.zero());
    assertThat(reward.getVkaiAmount()).isEqualTo(Amount.zero());
  }

  @Test
  @DisplayName("[getApr] apr을 보여줍니다.")
  void getApr() {
    double apr = this.httpKaiProtocolClient.getApr();
    assertThat(apr).isNotEqualTo(0);
  }

  @Test
  @DisplayName("[getTvl] tvl을 보여줍니다.")
  void getTvl() {
    double tvl = this.httpKaiProtocolClient.getTvl();
    assertThat(tvl).isNotEqualTo(0);
  }

  @Test
  @DisplayName("[getTotalTvl] total tvl을 보여줍니다.")
  void getTotalTvl() {
    double totalTvl = this.httpKaiProtocolClient.getTotalTvl();
    assertThat(totalTvl).isNotEqualTo(0);
  }
}
