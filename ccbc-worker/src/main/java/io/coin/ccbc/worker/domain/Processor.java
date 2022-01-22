package io.coin.ccbc.worker.domain;

import java.util.List;

public interface Processor<I, O> {

  List<O> process(List<I> items, WorkerExecutionContext context);
}
