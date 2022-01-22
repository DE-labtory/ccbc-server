package io.coin.ccbc.infra.exchange.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinOneProperties {

  public static final String COIN_ONE_PROPERTIES_PREFIX = "app.exchange.coin-one";

  private String host;
}
