package io.coin.ccbc.api.config.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DevDataInitializer implements CommandLineRunner {

  private final CoinDataInitializer coinDataInitializer;
  private final PoolDataInitializer poolDataInitializer;
  private final KlayfiDataInitializer klayfiDataInitializer;
  private final DefinixDataInitializer definixDataInitializer;

  public DevDataInitializer(
      CoinDataInitializer coinDataInitializer,
      PoolDataInitializer poolDataInitializer,
      KlayfiDataInitializer klayfiDataInitializer,
      DefinixDataInitializer definixDataInitializer
  ) {
    this.coinDataInitializer = coinDataInitializer;
    this.poolDataInitializer = poolDataInitializer;
    this.klayfiDataInitializer = klayfiDataInitializer;
    this.definixDataInitializer = definixDataInitializer;
  }

  @Override
  @Transactional
  public void run(String... args) {
    this.coinDataInitializer.run();
    this.poolDataInitializer.run();
    this.klayfiDataInitializer.run();
    this.definixDataInitializer.run();
  }
}
