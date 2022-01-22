package io.coin.ccbc.worker.config;

import io.coin.ccbc.jpa.annotation.EnableDefaultJpa;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableDefaultJpa
@EnableJpaRepositories(basePackages = {
    "io.coin.ccbc.domain",
    "io.coin.ccbc.klayfi",
    "io.coin.ccbc.klayswap",
    "io.coin.ccbc.definix",
})
@EntityScan(basePackages = {
    "io.coin.ccbc.domain",
    "io.coin.ccbc.klayfi",
    "io.coin.ccbc.klayswap",
    "io.coin.ccbc.definix",
})
public class JpaConfig {

}
