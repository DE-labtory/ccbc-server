package io.coin.ccbc.worker.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WorkerExecutionContext {

  private final Map<String, Object> context;

  public WorkerExecutionContext() {
    this.context = new HashMap<>();
  }

  public void put(String key, Object value) {
    this.context.put(key, value);
  }

  public Object get(String key) {
    return this.context.get(key);
  }

  public Object getOrDefault(String key, Object defaultValue) {
    return this.context.getOrDefault(key, defaultValue);
  }

  public <X extends Throwable> Object getOrThrow(String key,
      Supplier<? extends X> exceptionSupplier) throws X {
    Object value = this.context.get(key);
    if (value != null) {
      return value;
    } else {
      throw exceptionSupplier.get();
    }
  }

  public void clear() {
    this.context.clear();
  }
}
