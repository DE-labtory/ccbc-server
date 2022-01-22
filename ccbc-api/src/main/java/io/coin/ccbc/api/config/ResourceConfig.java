package io.coin.ccbc.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfig {

  @Bean
  @ConfigurationProperties(ResourceProperties.RESOURCE_PROPERTIES_PREFIX)
  public ResourceProperties resourceProperties() {
    return new ResourceProperties();
  }

  @Bean
  public String coinImageBaseUrl() {
    return this.resourceProperties().getStorageUrl()
        + "/"
        + this.resourceProperties().getBucketName()
        + "/"
        + this.resourceProperties().getCoinImagePath()
        + "/"
        ;
  }
}
