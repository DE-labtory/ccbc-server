package io.coin.ccbc.worker.config;

import io.coin.ccbc.infra.slack.config.SlackAlerterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SlackAlerterConfig.class)
public class AlerterConfig {

}
