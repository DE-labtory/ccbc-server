package io.coin.ccbc.api.config;

import io.coin.ccbc.domain.BlockchainClient;
import io.coin.ccbc.domain.DexPairRepository;
import io.coin.ccbc.domain.DexTradeFinder;
import io.coin.ccbc.jpa.IdGenerator;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public IdGenerator idGenerator() throws NoSuchAlgorithmException {
    return new IdGenerator();
  }


  @Bean
  public DexTradeFinder dexTradeFinder(
      BlockchainClient blockchainClient,
      DexPairRepository dexPairRepository
  ) {
    return new DexTradeFinder(blockchainClient, dexPairRepository);
  }
}
