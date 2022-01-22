package io.coin.ccbc.infra.blockchain.annotations;

import io.coin.ccbc.infra.blockchain.config.KlaytnConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

public final class EnableBlockchainClient {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Documented
  @Import(value = KlaytnConfig.class)
  public @interface EnableKlaytnClient {

  }
}