package io.coin.ccbc.kronos.infra;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.kronos.infra.selenium.SeleniumKronosCrawler;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpKronosClientTests {
    private static SeleniumKronosCrawler seleniumKronosCrawler ;
    private HttpKronosClient httpKronosClient;

    @BeforeAll
    static void beforeAll() throws IOException, InterruptedException {
        seleniumKronosCrawler = new SeleniumKronosCrawler(10000);
    }

    @AfterAll
    static void tearDown() {
        seleniumKronosCrawler.close();
    }

    @BeforeEach
    void setUp() {
        this.httpKronosClient = new HttpKronosClient(
                "http://34.64.244.237?clientId=2ac310ec4eea5a958f1b827943a48148",
                seleniumKronosCrawler
        );
    }

    @Test
    @DisplayName("[getStakedBalance] 예치된 잔고를 조회한다")
    void getStakedBalance() {
        Amount amount = this.httpKronosClient.getStakedBalance(
                Address.of("0xCA5e65f9E394c47FF3585e21612d32ABc3436501"));
        assertThat(amount).isNotEqualTo(Amount.zero());
    }

    @Test
    @DisplayName("[getStakedBalance] 예치된 잔고를 조회한다")
    void getStakedBalance_WhenNoDeposit() {
        Amount amount = this.httpKronosClient.getStakedBalance(
                Address.of("0x2953FdaDF72c0d89343bD48e81241d2698aD1a1A"));
        assertThat(amount).isEqualTo(Amount.zero());
    }

    @Test
    @DisplayName("[getApy] apy를 보여줍니다.")
    void getApr() {
        double apy = this.httpKronosClient.getApy();
        assertThat(apy).isNotEqualTo(0);
    }

    @Test
    @DisplayName("[getTotalTvl] total tvl을 보여줍니다.")
    void getTotalTvl() {
        double totalTvl = this.httpKronosClient.getTotalTvl();
        assertThat(totalTvl).isNotEqualTo(0);
    }
}
