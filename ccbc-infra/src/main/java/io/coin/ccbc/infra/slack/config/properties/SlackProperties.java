package io.coin.ccbc.infra.slack.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlackProperties {

  public static final String SLACK_PROPERTIES_PREFIX = "app.slack";
  private String token;
  private Boolean enable;
}