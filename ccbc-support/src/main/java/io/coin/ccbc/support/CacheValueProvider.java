package io.coin.ccbc.support;

public interface CacheValueProvider {

  Object load(Object key) throws Exception;
}
