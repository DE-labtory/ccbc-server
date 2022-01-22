package io.coin.ccbc.infra.exchange.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bomi
 * @date 2021/08/07
 */

@Configuration
public class OkHttpClientConfig {

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        .connectionPool(new ConnectionPool())
        .build();
  }
}
