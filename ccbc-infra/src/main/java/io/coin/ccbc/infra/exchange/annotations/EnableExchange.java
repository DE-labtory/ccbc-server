package io.coin.ccbc.infra.exchange.annotations;

import io.coin.ccbc.infra.exchange.config.CoinOneConfig;
import io.coin.ccbc.infra.exchange.config.KorbitConfig;
import io.coin.ccbc.infra.exchange.config.OkHttpClientConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {
    OkHttpClientConfig.class,
    CoinOneConfig.class,
    KorbitConfig.class,
})
public @interface EnableExchange {

}
