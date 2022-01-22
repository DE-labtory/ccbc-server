package io.coin.ccbc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Bomi
 * @date 2021/07/14
 */

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
    "io.coin.ccbc.domain",
    "io.coin.ccbc.klayfi",
    "io.coin.ccbc.klayswap",
    "io.coin.ccbc.definix",
})
@EntityScan(basePackages = {
    "io.coin.ccbc.domain",
    "io.coin.ccbc.klayswap",
    "io.coin.ccbc.definix",
    "io.coin.ccbc.klayfi"
})
@ComponentScan(basePackages = {
    "io.coin.ccbc.domain",
    "io.coin.ccbc.api",
    "io.coin.ccbc.klayswap",
    "io.coin.ccbc.definix",
    "io.coin.ccbc.klayfi"
})
@EnableCaching
public class CcbcApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CcbcApiApplication.class);
  }
}
