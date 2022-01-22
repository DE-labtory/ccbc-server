package io.coin.ccbc.api.config.init;

import io.coin.ccbc.definix.DefinixFarm;
import io.coin.ccbc.definix.DefinixFarmRepository;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import javax.transaction.Transactional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefinixDataInitializer {

  private final CoinRepository coinRepository;
  private final DefinixFarmRepository definixFarmRepository;
  public static final Address KWTBC_KUSDT_PAIR_ADDR = Address.of("0xd7e66f34d76779396ed62e0f097be4a82d2f4b57");
  public static final int KWTBC_KUSDT_PARI_PID = 12;
  public DefinixDataInitializer(CoinRepository coinRepository,
      DefinixFarmRepository definixFarmRepository) {
    this.coinRepository = coinRepository;
    this.definixFarmRepository = definixFarmRepository;
  }

  @Transactional
  public void run() {
    this.definixFarmRepository.findByAddress(KWTBC_KUSDT_PAIR_ADDR)
        .orElseGet(()->{
          Coin kwbtc = coinRepository.findBySymbolOrElseThrow("KWBTC");
          Coin kusdt = coinRepository.findBySymbolOrElseThrow("KUSDT");
          return definixFarmRepository.save(
              new DefinixFarm(
                  KWTBC_KUSDT_PAIR_ADDR,
                  18,
                  KWTBC_KUSDT_PARI_PID,
                  kwbtc,
                  kusdt
                  )
          );
        });
  }
}
