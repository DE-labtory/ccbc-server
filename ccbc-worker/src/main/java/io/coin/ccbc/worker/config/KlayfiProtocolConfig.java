package io.coin.ccbc.worker.config;

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
      KlaytnProperties klaytnProperties,
      CoinRepository coinRepository
  )
      throws InterruptedException {
    return new Klayfi(
        klayfiCommodityRepository,
        new SeleniumKlayfiCrawler(1000),
        new HttpKlayfiClient(
            klaytnProperties.getNodeUrl(),
            klaytnProperties.getContracts().getKlayfiViewerAddress()
        ),
        coinRepository
    );
  }
}
