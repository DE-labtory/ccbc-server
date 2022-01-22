package io.coin.ccbc.infra.slack.config;


import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import io.coin.ccbc.infra.slack.SlackAlerterFactory;
import io.coin.ccbc.infra.slack.config.properties.SlackProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackAlerterConfig {

  @Bean
  @ConfigurationProperties(SlackProperties.SLACK_PROPERTIES_PREFIX)
  public SlackProperties SlackProperties() {
    return new SlackProperties();
  }

  @Bean
  public Slack slack() {
    return Slack.getInstance(SlackConfig.DEFAULT);
  }

  @Bean
  public SlackAlerterFactory slackAlerterFactory(
      Slack slack,
      SlackProperties slackProperties
  ) {
    return new SlackAlerterFactory(
        slack,
        slackProperties.getToken(),
        !slackProperties.getEnable()
    );
  }
}
