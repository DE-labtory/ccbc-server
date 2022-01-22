package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.lang.Nullable;

@Getter
// TODO: time이 같을 경우에 대한 핸들링이 안되어있음.
public class HistoryTimeSeries<T extends History> {

  private ListOrderedMap<LocalDateTime, T> map;

  public HistoryTimeSeries(List<T> data) {
    this.init(data);
  }

  private void init(List<T> data) {
    this.map = ListOrderedMap.listOrderedMap(
        data.stream()
            .sorted(Comparator.comparing(History::getCollectedAt))
            .collect(Collectors
                .toMap(History::getCollectedAt, d -> d, (x, y) -> y, LinkedHashMap::new)));
  }

  public int size() {
    return this.map.size();
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public void put(int index, LocalDateTime time, T data) {
    this.map.put(index, time, data);
    this.init(this.map.valueList());
  }

  public void put(LocalDateTime time, T data) {
    this.map.put(time, data);
    this.init(this.map.valueList());
  }

  public Integer getIndex(LocalDateTime time) {
    return this.map.indexOf(time);
  }

  @Nullable
  public T get(int index) {
    return this.map.getValue(index);
  }

  @Nullable
  public T get(LocalDateTime time) {
    return this.map.get(time);
  }

  /*
   TODO: naming
   해당 time에 data가 존재하지 않으면 존재할때 까지 back해서 search한다.
   */
  @Nullable
  public T getPreviousIfNotExist(LocalDateTime time) {
    if (this.isEmpty()) {
      return null;
    }

    T value = this.map.get(time);
    if (value != null) {
      return value;
    }

    if (this.map.size() == 1 && this.map.get(0).isBefore(time)) {
      return this.map.getValue(0);
    }

    for (int index = 1; index < this.size(); index++) {
      if (this.map.get(index - 1).isBefore(time) && this.map.get(index).isAfter(time)) {
        return this.map.getValue(index - 1);
      }
    }

    if (this.map.get(this.size() - 1).isBefore(time)) {
      return this.map.getValue(this.size() - 1);
    }
    return null;
  }
}
