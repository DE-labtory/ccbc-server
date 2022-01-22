package io.coin.ccbc.api.domain;

import org.springframework.data.domain.Page;

public interface PaginationFactory<T> {

  Pagination<T> create(Page<T> p);
}
