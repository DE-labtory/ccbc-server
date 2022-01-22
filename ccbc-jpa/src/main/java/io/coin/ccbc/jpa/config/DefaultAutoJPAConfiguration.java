package io.coin.ccbc.jpa.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "kstDateTimeProvider")
public class DefaultAutoJPAConfiguration {

  @Bean
  public DateTimeProvider kstDateTimeProvider() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    return () -> {
      String now = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);
      return Optional.of(LocalDateTime.parse(now, formatter));
    };
  }
}
