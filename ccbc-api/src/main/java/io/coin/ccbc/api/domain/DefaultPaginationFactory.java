package io.coin.ccbc.api.domain;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DefaultPaginationFactory<T> implements PaginationFactory<T> {

  private final HttpServletRequest request;

  public DefaultPaginationFactory(HttpServletRequest request) {
    this.request = request;
  }

  public Pagination<T> create(Page<T> p) {
    return new Pagination<>(this.buildNextUrl(p), this.buildPreviousUrl(p), p.getTotalElements(),
        p.getContent());
  }

  private String buildPreviousUrl(Page<T> p) {
    return p.isFirst() ? null : this.buildPageReplacedUrl(p.getPageable().getPageNumber() - 1);
  }

  private String buildNextUrl(Page<T> p) {
    return p.isLast() ? null : this.buildPageReplacedUrl(p.getPageable().getPageNumber() + 1);
  }

  private String buildPageReplacedUrl(int page) {
    return UriComponentsBuilder
        .fromUriString(this.request.getRequestURL().toString())
        .query(this.request.getQueryString())
        .replaceQueryParam("page", new Object[]{page})
        .build()
        .toUriString();
  }
}
