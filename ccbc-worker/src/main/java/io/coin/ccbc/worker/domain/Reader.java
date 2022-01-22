package io.coin.ccbc.worker.domain;

import java.util.List;

public interface Reader<I> {

  List<I> read(WorkerExecutionContext context);
}
