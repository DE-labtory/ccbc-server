package io.coin.ccbc.infra.exchange.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.coin.ccbc.infra.exchange.CoinOneHttpApis;
import io.coin.ccbc.infra.exchange.CoinOnePriceProvider;
import io.coin.ccbc.infra.exchange.config.properties.CoinOneProperties;
import io.coin.ccbc.infra.exchange.factory.ObjectMapperFactory;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class CoinOneConfig {

  @Bean
  @ConfigurationProperties(CoinOneProperties.COIN_ONE_PROPERTIES_PREFIX)
  public CoinOneProperties CoinOneProperties() {
    return new CoinOneProperties();
  }

  @Bean
  public CoinOneHttpApis coinOneHttpApis(
      CoinOneProperties coinOneProperties,
      OkHttpClient okHttpClient
  ) {
    ObjectMapper objectMapper = ObjectMapperFactory.snakeCaseObjectMapper();
    Retrofit coinOneRetrofit = new Retrofit.Builder()
        .baseUrl(coinOneProperties.getHost())
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(okHttpClient)
        .build();

    return coinOneRetrofit.create(CoinOneHttpApis.class);
  }

  @Bean
  @Qualifier("coinOnePriceProvider")
  public CoinOnePriceProvider coinOnePriceProvider(
      CoinOneHttpApis coinOneHttpApis
  ) {
    return new CoinOnePriceProvider(coinOneHttpApis);
  }
}
