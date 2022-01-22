package io.coin.ccbc.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/26
 */


@Getter
@AllArgsConstructor
public class LinkDto {

  private final String link;

  public static LinkDto from(String link) {
    return new LinkDto(link);
  }

}
