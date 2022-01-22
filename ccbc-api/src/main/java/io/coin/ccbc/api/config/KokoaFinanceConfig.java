package io.coin.ccbc.api.config;

import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.kokoa.HttpKoKoaProtocolClient;
import io.coin.ccbc.kokoa.KokoaFinance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KokoaFinanceConfig {

  @Bean
  public Defi kokoaFinance(
      CoinRepository coinRepository,
      HttpKoKoaProtocolClient httpKoKoaProtocolClient
  ) {
    return new KokoaFinance(
        httpKoKoaProtocolClient,
        coinRepository
    );
  }
}
