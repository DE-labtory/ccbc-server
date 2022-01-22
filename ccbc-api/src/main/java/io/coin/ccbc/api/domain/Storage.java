package io.coin.ccbc.api.domain;

public interface Storage<K, V> {

  V get(K key);

  void set(K key, V value);

  void remove(K key);

  void clear();
}
