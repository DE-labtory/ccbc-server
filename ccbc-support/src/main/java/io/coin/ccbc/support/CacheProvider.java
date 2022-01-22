package io.coin.ccbc.support;

import java.time.Duration;
import java.util.List;

public interface CacheProvider {

  List<CacheInformation> getCacheInformation();

  interface CacheInformation {

    CacheValueProvider getCacheValueProvider();

    String getCacheName();

    Duration refreshTime();
  }
}
