package io.coin.ccbc.worker.config;

import io.coin.ccbc.domain.BlockchainClient;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.DexPairRepository;
import io.coin.ccbc.domain.DexTradeFinder;
import io.coin.ccbc.infra.exchange.CoinOnePriceProvider;
import io.coin.ccbc.infra.exchange.KorbitPriceProvider;
import io.coin.ccbc.support.alerter.Alerter;
import io.coin.ccbc.support.alerter.AlerterFactory;
import io.coin.ccbc.worker.config.prorperty.WorkerProperties;
import io.coin.ccbc.worker.domain.DexPriceProvider;
import io.coin.ccbc.worker.domain.ExchangePriceProvider;
import io.coin.ccbc.worker.domain.MarketPriceResolver;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfig {

  @Bean
  @Qualifier("priceHistorySyncerDuration")
  public Duration priceHistorySyncerDuration(WorkerProperties properties) {
    return Duration.ofMinutes(properties.getPriceHistorySyncerDuration());
  }

  @Bean
  @Qualifier("priceHistorySyncerInterval")
  public Duration priceHistorySyncerInterval(WorkerProperties properties) {
    return Duration.ofMinutes(properties.getPriceHistorySyncerInterval());
  }

  @Bean
  public Alerter workerAlerter(AlerterFactory alerterFactory) {
    return alerterFactory.create("#worker_alert");
  }

  @Bean
  public DexTradeFinder dexTradeFinder(
      BlockchainClient blockchainClient,
      DexPairRepository dexPairRepository
  ) {
    return new DexTradeFinder(blockchainClient, dexPairRepository);
  }

  @Bean
  public DexPriceProvider klaySwapDexPriceProvider(
      CoinRepository coinRepository,
      DexTradeFinder dexTradeFinder
  ) {
    return new DexPriceProvider(
        "KLAY",
        coinRepository,
        dexTradeFinder
    );
  }

  @Bean
  public ExchangePriceProvider exchangePriceProvider(
      CoinOnePriceProvider coinOnePriceProvider,
      KorbitPriceProvider korbitPriceProvider
  ) {
    return new ExchangePriceProvider(
        List.of(coinOnePriceProvider, korbitPriceProvider)
    );
  }

  @Bean
  public MarketPriceResolver priceResolver(
      @Qualifier("exchangePriceProvider") ExchangePriceProvider exchangePriceProvider,
      @Qualifier("klaySwapDexPriceProvider") DexPriceProvider klaySwapDexPriceProvider
  ) {
    return new MarketPriceResolver(exchangePriceProvider, klaySwapDexPriceProvider);
  }


}
