package io.coin.ccbc.api.config;

import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.infra.blockchain.config.properties.KlaytnProperties;
import io.coin.ccbc.kai.KaiProtocol;
import io.coin.ccbc.kai.infra.HttpKaiProtocolClient;
import io.coin.ccbc.kai.infra.selenium.SeleniumKaiProtocolCrawler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KaiProtocolConfig {

  @Bean
  public Defi kaiProtocol(CoinRepository coinRepository, KlaytnProperties klaytnProperties)
      throws InterruptedException {
    return new KaiProtocol(
        coinRepository,
        new HttpKaiProtocolClient(
            klaytnProperties.getNodeUrl(),
            new SeleniumKaiProtocolCrawler(1000)
        )
    );
  }
}
