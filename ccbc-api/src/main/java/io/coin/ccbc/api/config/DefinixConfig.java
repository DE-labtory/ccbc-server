package io.coin.ccbc.api.config;

import io.coin.ccbc.definix.DefinixAprCalculator;
import io.coin.ccbc.definix.Definix;
import io.coin.ccbc.definix.DefinixFarmRepository;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.infra.blockchain.klaytn.DefinixKlaytnClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefinixConfig {

  @Bean
  public Definix definix(
      DefinixKlaytnClient definixKlaytnClient,
      DefinixFarmRepository definixFarmRepository,
      DefinixAprCalculator aprCalculator,
      CoinRepository coinRepository
  ) {
    return new Definix(
        definixKlaytnClient,
        definixFarmRepository,
        aprCalculator,
        coinRepository
    );
  }
}
