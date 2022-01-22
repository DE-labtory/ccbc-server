package io.coin.ccbc.infra.exchange.factory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Bomi
 * @date 2021/08/07
 */

public class ObjectMapperFactory {

  private static ObjectMapper snakeCaseMapper;

  public static ObjectMapper snakeCaseObjectMapper() {
    if (snakeCaseMapper == null) {
      snakeCaseMapper = new ObjectMapper()
          .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .registerModules(new JavaTimeModule());
    }
    return snakeCaseMapper;
  }
}
