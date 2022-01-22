package io.coin.ccbc.infra.exchange.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bomi
 * @date 2021/08/07
 */

@Getter
@Setter
public class KorbitProperties {

  public static final String PROPERTIES_PREFIX = "app.exchange.korbit";

  private String host;
}
