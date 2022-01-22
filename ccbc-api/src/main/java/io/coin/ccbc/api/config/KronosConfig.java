package io.coin.ccbc.api.config;

import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.infra.blockchain.config.properties.KlaytnProperties;
import io.coin.ccbc.kronos.KronosDaoFinance;
import io.coin.ccbc.kronos.infra.HttpKronosClient;
import io.coin.ccbc.kronos.infra.selenium.SeleniumKronosCrawler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KronosConfig {

    @Bean
    public Defi kronos(CoinRepository coinRepository, KlaytnProperties klaytnProperties)
            throws InterruptedException {
        return new KronosDaoFinance(
                coinRepository,
                new HttpKronosClient(
                        klaytnProperties.getNodeUrl(),
                        new SeleniumKronosCrawler(1000)
                )
        );
    }
}
