package io.coin.ccbc.api.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceProperties {

  public static final String RESOURCE_PROPERTIES_PREFIX = "app.resource";

  private String storageUrl;
  private String bucketName;
  private String coinImagePath;
}
