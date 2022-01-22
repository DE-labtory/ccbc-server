package io.coin.ccbc.worker.domain;

import java.util.List;

public interface Writer<O> {

  void write(List<O> items);
}
