package io.coin.ccbc.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  @Bean
  public OpenAPI openAPI() {
    SpringDocUtils.getConfig().replaceWithClass(BigInteger.class, String.class);
    SpringDocUtils.getConfig().replaceWithClass(LocalDateTime.class, String.class);

    OpenAPI openAPI = new OpenAPI();
    Info info = new Info();
    info.setTitle("CCBC API");
    info.description("CCBC API 명세");
    openAPI.setInfo(info);
    return openAPI;
  }
}
