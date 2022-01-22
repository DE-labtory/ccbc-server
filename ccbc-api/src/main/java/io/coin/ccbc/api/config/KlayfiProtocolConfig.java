package io.coin.ccbc.api.config;

import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.infra.blockchain.config.properties.KlaytnProperties;
import io.coin.ccbc.klayfi.domain.Klayfi;
import io.coin.ccbc.klayfi.domain.KlayfiCommodityRepository;
import io.coin.ccbc.klayfi.infra.HttpKlayfiClient;
import io.coin.ccbc.klayfi.infra.SeleniumKlayfiCrawler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KlayfiProtocolConfig {

  @Bean
  public Defi klayfiProtocol(
      KlayfiCommodityRepository klayfiCommodityRepository,
      CoinRepository coinRepository,
      KlaytnProperties klaytnProperties,
      SeleniumKlayfiCrawler seleniumKlayfiCrawler
  ) {
    return new Klayfi(
        klayfiCommodityRepository,
        seleniumKlayfiCrawler,
        new HttpKlayfiClient(
            klaytnProperties.getNodeUrl(),
            klaytnProperties.getContracts().getKlayfiViewerAddress()
        ),
        coinRepository
    );
  }

  @Bean
  public SeleniumKlayfiCrawler seleniumKlayfiCrawler() throws InterruptedException {
    return new SeleniumKlayfiCrawler(1000);
  }
}
