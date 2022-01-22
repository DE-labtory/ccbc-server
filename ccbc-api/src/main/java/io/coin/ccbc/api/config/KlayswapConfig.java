package io.coin.ccbc.api.config;

import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.infra.blockchain.klaytn.KlayswapKlaytnClient;
import io.coin.ccbc.klayswap.Klayswap;
import io.coin.ccbc.klayswap.KlayswapAprCalculator;
import io.coin.ccbc.klayswap.KlayswapCommodityFetcher;
import io.coin.ccbc.klayswap.KlayswapPoolRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KlayswapConfig {
  @Bean
  public KlayswapCommodityFetcher klayswapCommodityFetcher(
      KlayswapKlaytnClient klayswapKlaytnClient,
      KlayswapPoolRepository klayswapPoolRepository,
      KlayswapAprCalculator klayswapAprCalculator
  ) {
    return new KlayswapCommodityFetcher(
        klayswapKlaytnClient,
        klayswapPoolRepository,
        klayswapAprCalculator
    );
  }

  @Bean
  public Klayswap klayswap(
      KlayswapKlaytnClient klayswapKlaytnClient,
      KlayswapPoolRepository klayswapPoolRepository,
      KlayswapCommodityFetcher klayswapCommodityFetcher,
      CoinRepository coinRepository
  ) {
    return new Klayswap(
        klayswapKlaytnClient,
        klayswapPoolRepository,
        coinRepository,
        klayswapCommodityFetcher
    );
  }
}
