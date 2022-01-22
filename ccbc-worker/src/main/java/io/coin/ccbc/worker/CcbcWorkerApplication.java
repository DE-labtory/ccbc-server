package io.coin.ccbc.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "io.coin.ccbc.domain",
    "io.coin.ccbc.klayswap",
    "io.coin.ccbc.definix",
    "io.coin.ccbc.klayfi",
    "io.coin.ccbc.kai",
    "io.coin.ccbc.worker"
})
public class CcbcWorkerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CcbcWorkerApplication.class);
  }
}
