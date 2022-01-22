package io.coin.ccbc.jpa.annotation;

import io.coin.ccbc.jpa.config.DefaultAutoJPAConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DefaultAutoJPAConfiguration.class)
public @interface EnableDefaultJpa {

}
