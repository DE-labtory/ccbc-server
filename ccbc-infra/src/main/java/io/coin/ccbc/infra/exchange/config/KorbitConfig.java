package io.coin.ccbc.infra.exchange.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.coin.ccbc.infra.exchange.KorbitHttpApis;
import io.coin.ccbc.infra.exchange.KorbitPriceProvider;
import io.coin.ccbc.infra.exchange.config.properties.KorbitProperties;
import io.coin.ccbc.infra.exchange.factory.ObjectMapperFactory;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Bomi
 * @date 2021/08/07
 */

@Configuration
public class KorbitConfig {

  @Bean
  @ConfigurationProperties(KorbitProperties.PROPERTIES_PREFIX)
  public KorbitProperties korbitProperties() {
    return new KorbitProperties();
  }

  @Bean
  public KorbitHttpApis korbitHttpApis(
      KorbitProperties korbitProperties,
      OkHttpClient okHttpClient
  ) {
    ObjectMapper objectMapper = ObjectMapperFactory.snakeCaseObjectMapper();
    Retrofit korbitRetrofit = new Retrofit.Builder()
        .baseUrl(korbitProperties.getHost())
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(okHttpClient)
        .build();

    return korbitRetrofit.create(KorbitHttpApis.class);
  }

  @Bean
  @Qualifier("korbitPriceProvider")
  public KorbitPriceProvider korbitPriceProvider(
      KorbitHttpApis korbitHttpApis
  ) {
    return new KorbitPriceProvider(korbitHttpApis);
  }
}
